package cass.wsd;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import cass.languageTool.*;
import cass.languageTool.wordNet.CASSWordSense;

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
		context.addAll(lTool.tokenizeAndLemmatize(leftContext));
		context.addAll(lTool.tokenizeAndLemmatize(rightContext));
		}
	
	/**
	 * public WSD method
	 * @param algorithm - member of Algorithm enumeration representing which algorithm to run
	 * @return sorted List of senses scored with algorithm of choice
	 */
	public List<CASSWordSense> rankSensesUsing(Algorithm algorithm) {
		
		List<ScoredSense> scoredSenses = scoreSensesUsing(algorithm);
		List<CASSWordSense> rankedSenses = new ArrayList<CASSWordSense>();
		
		// convert ScoredSense to WordSense, discard score
		for (ScoredSense wordSense : scoredSenses) {
			rankedSenses.add(wordSense.getSense());
		}
		
		return rankedSenses;
	}
	
	/**
	 * Converts Algorithm parameter to WSD method call. Package visibility so that score information can be used in JUint tests.
	 * @param algorithm - member of Algorithm enumeration representing which algorithm to run
	 * @return sorted List of ScoredSenses scored according to alforithm of choice
	 */
	List<ScoredSense> scoreSensesUsing(Algorithm algorithm) {
		
		List<ScoredSense> scoredSenses = null;
		
		switch (algorithm) {
		case LESK:
			scoredSenses = scoreSensesUsingLesk();
			break;

		case STOCHASTIC_GRAPH:
			scoredSenses = scoreSensesUsingStochasticHypernymDistance();
			break;
			
		case FREQUENCY:
			scoredSenses = scoreSensesUsingTagFrequency();
			break;
			
		case RANDOM:
			scoredSenses = scoreSensesRandomly();
			break;
			
		case LESK_WITH_FREQUENCY_FILTER:
			scoredSenses = scoreSensesUsingLeskAndFilter();
			break;
			
		default:
			// TODO throw proper exception
			break;
		}
		
		return scoredSenses;
	}
	
	/**
	 * Custom WSD algorithm that filters the target senses to get a more fine-grained sense inventory before
	 * running Lesk's algorithm on the target senses.
	 * @return sorted List of ScoredSenses scored on intersection of filtered target sense gloss and context
	 */
	private List<ScoredSense> scoreSensesUsingLeskAndFilter() {
		Set<CASSWordSense> allTargetSenses = lTool.getSenses(target);
		Set<CASSWordSense> filteredSenses = new HashSet<CASSWordSense>();
		
		// filter allTargetSenses, save to filteredSenses
		
		// TODO: FAUSTO
		return leskIntenal(filteredSenses);
	}
	
	/**
	 * WSD implementation of Lesk's algorithm
	 * @return sorted List of ScoredSenses scored on intersection of target sense gloss and context
	 */
	private List<ScoredSense> scoreSensesUsingLesk() {
		return leskIntenal(lTool.getSenses(target));
	}
	
	/**
	 * Implements the details of Lesk's algorithm.
	 * score is the cardinality of the  intersection of the tokens the the gloss set of each target sense and the tokens of the context.
	 * @param targetSenses - senses that are to be considered
	 * @return sorted List of ScoredSenses scored on token intersection
	 */
	private List<ScoredSense> leskIntenal(Set<CASSWordSense> targetSenses) {
		// context set is set of words in context
				Set<String> contextSet = new HashSet<String>(context);
				
				Set<String> glossSet = new HashSet<String>();
				List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
				
				// for every set of synonyms in the list
				for (CASSWordSense targetSense : targetSenses) {
					// clear and add lemmatized tokens of gloss to set
					glossSet.clear();
					String definition = lTool.getDefinition(targetSense);
					glossSet.addAll(lTool.tokenizeAndLemmatize(definition));
					
					// find intersection of sets
					glossSet.retainAll(contextSet);
					
					// score is cardinality of intersection
					int score = glossSet.size();
					
					scoredSenses.add(new ScoredSense(targetSense, score));
				}
				
				// sort in descending order
				Collections.sort(scoredSenses);
				Collections.reverse(scoredSenses);
				
				return scoredSenses;
	}
	 /**
	  * WSD algorithm to score senses based on lowest common hypernym ancestor to senses of context words.
	  * The algorithm assigns a score to a target sense that is the sum of the minimum distances between it and the senses of the context words.
	  * The stochastic part of this algorithm relies on randomly selecting a hypernym in the case there are multiple.
	  * @return sorted List of ScoredSenses scored by hypernym traversals to the senses of each context word.
	  */
	private List<ScoredSense> scoreSensesUsingStochasticHypernymDistance() {
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
				
		for (CASSWordSense targetSense : lTool.getSenses(target)) {
			
			Integer senseScore = null;
			
			if (targetSense.getPOS() == "noun") {
				senseScore = scoreTargetSensesUsingHypernymDistance(targetSense);
			}
			
			if (senseScore == null) {
				senseScore = Integer.MAX_VALUE;
			}
			
			scoredSenses.add(new ScoredSense(targetSense, senseScore));
		}
		
		// sort in ascending order
		Collections.sort(scoredSenses);
		
		return scoredSenses;
	}
	
	/**
	 * Helper method to hypernym distance algorithm.
	 * @param targetSense - some sense to score against context by hypernym distance
	 * @return - Integer score, smaller is better
	 */
	private Integer scoreTargetSensesUsingHypernymDistance(CASSWordSense targetSense) {
		
		List<CASSWordSense> targetHypernymChain = lTool.getHypernymAncestors(targetSense);
		if ((targetHypernymChain == null) || (targetHypernymChain.isEmpty())) {
			return null;
		}
		
		int senseScore = 0;
		
		// loop over all context words
		for (String contextWord : context) {
			Set<CASSWordSense> contextWordSenses = lTool.getSenses(contextWord);
			int contextWordBestScore = Integer.MAX_VALUE;
			if (!contextWordSenses.isEmpty()) {
				// loop over all senses of context word
				for (CASSWordSense contextWordSense : contextWordSenses) {
					if (contextWordSense.getPOS() == "noun") {
						List<CASSWordSense> contextWordHypernymChain = lTool.getHypernymAncestors(contextWordSense);
						Integer score = lTool.getHypernymDistanceScore(targetHypernymChain, contextWordHypernymChain);
						if ((score != null) && (score < contextWordBestScore)) {
							contextWordBestScore = score;
						}
					}
				}
			}
			if (contextWordBestScore == Integer.MAX_VALUE) {
				contextWordBestScore = 0;
			}
			senseScore += contextWordBestScore;
		}
		return senseScore;
	}
	
	/**
	 * Real baseline algorithm. Implements selection of sense based on sense frequency. The results of this algorithm are the baseline
	 * that the WSD algorithms should beat.
	 * @return sorted List of ScoredSenses scored by frequency distribution of sense
	 */
	private List<ScoredSense> scoreSensesUsingTagFrequency() {
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
				
		for (CASSWordSense sense : lTool.getSenses(target)) {
			scoredSenses.add(new ScoredSense(sense, sense.getTagFrequency()));
		}

		Collections.sort(scoredSenses);
		Collections.reverse(scoredSenses);
		
		return scoredSenses;
	}
	
	/**
	 * Simple baseline algorithm. Chooses a random score for each sense and then sorts the senses based on the score.
	 * @return sorted List of ScoredSenses with randomly generated scores
	 */
	private List<ScoredSense> scoreSensesRandomly() {
		Random rand = new Random();
		
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
		
		for (CASSWordSense sense : lTool.getSenses(target)) {
			scoredSenses.add(new ScoredSense(sense, rand.nextInt()));
		}
		
		Collections.sort(scoredSenses);
		Collections.reverse(scoredSenses);
		
		return scoredSenses;
	}
}