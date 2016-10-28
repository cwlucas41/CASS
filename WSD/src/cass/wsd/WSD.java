package cass.wsd;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import cass.languageTool.*;
import cass.languageTool.wordNet.CASSWordSense;
import cass.wsd.algorithm.*;

/**
 * General purpose Word Sense Disambiguation class. It has been designed not only to 
 * give a best sense for a word, but also to rank senses of a word with respect to 
 * each other. The output of the WSD algorithms is a List of senses that are in sorted
 * order.
 * 
 * @author cwlucas41
 *
 */
public class WSD {
	
	private LanguageTool lTool;
	private List<String> context;
	private String target;
		
	/**
	 * Constructor for WSD. 
	 * @param leftContext - String of words left of the target word in corpus
	 * @param target - target word String
	 * @param rightContext - String of words right of the target word in corpus
	 * @param language - Language of text, member of Language enumeration
	 */
	public WSD(String leftContext, String target, String rightContext, Language language) {
		lTool = new LanguageTool(language);
		
		this.target = target;
		
		context = new ArrayList<String>();
		if (leftContext != null)
			context.addAll(lTool.tokenizeAndLemmatize(leftContext));
		if (rightContext != null)
			context.addAll(lTool.tokenizeAndLemmatize(rightContext));
		}
	
	/**
	 * Gets the Language Tool of the class. The language tool is initialized to the language of the WSD class.
	 * @return language tool
	 */
	public LanguageTool getlTool() {
		return lTool;
	}
	
	public String getTarget() {
		return target;
	}

	/**
	 * gets the context for the WSD object. The context is a List of strings. Tokenization and Lemmatization have already taken place
	 * @return lemmatized and tokenized context in a List
	 */
	public List<String> getContext() {
		return context;
	}

	/**
	 * public WSD method
	 * @param algorithm - member of Algorithm enumeration representing which algorithm to run
	 * @param frequencyThreshold for filtering senses
	 * @return sorted List of senses scored with algorithm of choice
	 */
	public List<CASSWordSense> rankSensesUsing(Algorithm algorithm, double frequencyThreshold) {
		
		List<ScoredSense> scoredSenses = scoreSensesUsing(algorithm, frequencyThreshold);
		List<CASSWordSense> rankedSenses = new ArrayList<CASSWordSense>();
		
		// convert ScoredSense to WordSense, discard score
		for (ScoredSense wordSense : scoredSenses) {
			Set<String> senseSynonyms = lTool.getSynonyms(wordSense.getSense());
			String exampleSynonym = senseSynonyms.iterator().next();
			char firstLetter = exampleSynonym.charAt(0);
			if (!Character.isUpperCase(firstLetter)) {
				rankedSenses.add(wordSense.getSense());
			}
		}
		return rankedSenses;
	}
	
	/**
	 * Converts Algorithm parameter to WSD method call. Package visibility so that score information can be used in JUint tests.
	 * @param algorithm - member of Algorithm enumeration representing which algorithm to run
	 * @param frequencyThreshold for filtering senses
	 * @return sorted List of ScoredSenses scored according to algorithm of choice
	 */
	List<ScoredSense> scoreSensesUsing(Algorithm algorithm, double frequencyThreshold) {
		
		List<ScoredSense> scoredSenses = null;
		I_WSDAlgorithm alg;
		
		Set <CASSWordSense> relevantSenses;
		
		if (frequencyThreshold != 0) {
			relevantSenses = getFilteredTargetSenses(frequencyThreshold);
		} else {
			relevantSenses = getAllTargetSenses();
		}
		
		if (context.isEmpty())
			algorithm = Algorithm.FREQUENCY;
		
		switch (algorithm) {
		case LESK:
			alg = new LeskAlgorithm(this);
			scoredSenses = alg.score(relevantSenses);
			break;
			
		case CUSTOM_LESK:
			alg = new CustomLeskAlgorithm(this);
			scoredSenses = alg.score(relevantSenses);
			break;

		case HYPERNYM_DISTANCE:
			alg = new HypernymDistanceAlgorithm(this);
			scoredSenses = alg.score(relevantSenses);
			break;
			
		case RANDOM:
			alg = new RandomAlgorithm();
			scoredSenses = alg.score(relevantSenses);
			break;
						
		case FREQUENCY:
			alg = new FrequencyAlgorithm();
			scoredSenses = alg.score(relevantSenses);
			break;
			
		default:
			break;
		}
		
		return scoredSenses;
	}
	
	/**
	 * filters senses to some frequency threshold
	 * @param senses - input senses
	 * @param threshold for filtering senses
	 * @return filtered set of target senses
	 */
	public Set<CASSWordSense> filterSensesToFrequencyThreshold(Set<CASSWordSense> senses, double threshold) {
		// filter senses, save to filteredSenses
		
		Set<CASSWordSense> filteredSenses = new HashSet<CASSWordSense>();
		int total = 0;
		for(CASSWordSense sense : senses){			
			total += sense.getTagFrequency();
		}
		int minFrequency = (int)( total * threshold); // Floor of threshold of total usage
		
		for(CASSWordSense sense : senses){			
			if(sense.getTagFrequency() >= minFrequency){
				filteredSenses.add(sense);
			}
		}
		return filteredSenses;
	}
	
	private Set<CASSWordSense> getAllTargetSenses() {
		return lTool.getSenses(target);
	}
	
	private Set<CASSWordSense> getFilteredTargetSenses(double threshold) {
		return filterSensesToFrequencyThreshold(getAllTargetSenses(), threshold);
	}
}