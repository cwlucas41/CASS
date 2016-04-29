package cass.wsd;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import cass.languageTool.*;
import cass.languageTool.wordNet.CASSWordSense;
import cass.wsd.algorithm.*;

public class WSD {
	
	private LanguageTool lTool;
	private List<String> context;
	private String target;
	
	public WSD(String leftContext, String target, String rightContext, Language language) {
		lTool = new LanguageTool(language);
		
		this.target = target;
		
		context = new ArrayList<String>();
		context.addAll(lTool.tokenizeAndLemmatize(leftContext));
		context.addAll(lTool.tokenizeAndLemmatize(rightContext));
		}
	
	/**
	 * Gets the Language Tool of the class. The language tool is initialized to the language of the WSD class.
	 * @return language tool
	 */
	public LanguageTool getlTool() {
		return lTool;
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
		I_WSDAlgorithm alg = new LeskAlgorithm(this);
		return alg.score(filterTargetSensesToFrequencyThreshold());
	}
	
	private List<ScoredSense> scoreSensesUsingLesk() {
		I_WSDAlgorithm alg = new LeskAlgorithm(this);
		return alg.score(lTool.getSenses(target));
	}
	
	 /**
	  * WSD algorithm to score senses based on lowest common hypernym ancestor to senses of context words.
	  * The algorithm assigns a score to a target sense that is the sum of the minimum distances between it and the senses of the context words.
	  * The stochastic part of this algorithm relies on randomly selecting a hypernym in the case there are multiple.
	  * @return sorted List of ScoredSenses scored by hypernym traversals to the senses of each context word.
	  */
	private List<ScoredSense> scoreSensesUsingStochasticHypernymDistance() {
		I_WSDAlgorithm alg = new HypernymDistanceAlgorithm(this);
		return alg.score(lTool.getSenses(target));
	}
	
	private List<ScoredSense> scoreSensesUsingTagFrequency() {
		I_WSDAlgorithm alg = new FrequencyAlgorithm();
		return alg.score(lTool.getSenses(target));
	}
	
	/**
	 * Simple baseline algorithm for other algorithms that use filter. Similar to random.
	 * @return
	 */
	private List<ScoredSense> scoreFilteredSensesRandomly() {
		I_WSDAlgorithm randomAlg= new RandomAlgorithm();
		return randomAlg.score(filterTargetSensesToFrequencyThreshold());
	}
	
	/**
	 * Simple baseline algorithm. Chooses a random score for each sense and then sorts the senses based on the score.
	 * @return sorted List of ScoredSenses with randomly generated scores
	 */
	private List<ScoredSense> scoreSensesRandomly() {
		I_WSDAlgorithm randomAlg= new RandomAlgorithm();
		return randomAlg.score(lTool.getSenses(target));
	}
	
	/**
	 * filters target senses to some threshold
	 * @return filtered set of target senses
	 */
	private Set<CASSWordSense> filterTargetSensesToFrequencyThreshold() {
		// filter allTargetSenses, save to filteredSenses
		double threshold = 0.3;

		Set<CASSWordSense> allTargetSenses = lTool.getSenses(target);
		Set<CASSWordSense> filteredSenses = new HashSet<CASSWordSense>();
		int total =0;
		for(CASSWordSense sense : allTargetSenses){			
			total+=sense.getTagFrequency();
		}
		int minFrequency =  (int)( total * threshold); // Floor of threshold of total usage
		
		for(CASSWordSense sense : allTargetSenses){			
			if(sense.getTagFrequency() >= minFrequency){
				filteredSenses.add(sense);
			}
		}
		return filteredSenses;
	}
}