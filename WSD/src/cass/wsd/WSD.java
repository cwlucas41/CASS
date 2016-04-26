package cass.wsd;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import cass.languageTool.*;
import cass.languageTool.wordNet.CASSWordSense;

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
			// TODO throw proper exception
			break;
		}
		
		return scoredSenses;
	}
	
	private List<ScoredSense> scoreSensesUsingLeskAndFilter() {
		Set<CASSWordSense> allTargetSenses = lTool.getSenses(target);
		Set<CASSWordSense> filteredSenses = new HashSet<CASSWordSense>();
		
		// filter allTargetSenses, save to filteredSenses
		
		// TODO: FAUSTO
		return leskIntenal(filteredSenses);
	}
	
	private List<ScoredSense> scoreSensesUsingLesk() {
		return leskIntenal(lTool.getSenses(target));
	}
	
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
	
	private List<ScoredSense> scoreSensesUsingTagFrequency() {
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
				
		for (CASSWordSense sense : lTool.getSenses(target)) {
			scoredSenses.add(new ScoredSense(sense, sense.getTagFrequency()));
		}

		Collections.sort(scoredSenses);
		Collections.reverse(scoredSenses);
		
		return scoredSenses;
	}
	
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