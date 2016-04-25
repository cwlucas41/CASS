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
	@SuppressWarnings("unused")
	private String target;
	private Random rand = new Random();
	Set<CASSWordSense> targetSenses;
	
	public WSD(String leftContext, String target, String rightContext, Language language) {
		lTool = new LanguageTool(language);
		
		this.target = target;
		
		context = new ArrayList<String>();
		context.addAll(lTool.tokenizeAndLemmatize(leftContext));
		context.addAll(lTool.tokenizeAndLemmatize(rightContext));
		
		targetSenses = lTool.getSenses(target);
		}
	
	public List<CASSWordSense> rankSynsetsUsing(Algorithm algorithm) {
		
		List<ScoredSense> scoredSenses;
		List<CASSWordSense> rankedSenses = new ArrayList<CASSWordSense>();
		
		switch (algorithm) {
		case LESK:
			scoredSenses = rankSensesUsingLesk();
			break;

		case STOCHASTIC_GRAPH:
			scoredSenses = rankSensesUsingStochasticHypernymDistance();
			break;
			
		case FREQUENCY:
			scoredSenses = rankSensesUsingTagFrequency();
			break;
			
		case RANDOM:
			scoredSenses = rankSensesRandomly();
			break;
			
		default:
			// TODO throw proper exception
			return null;
		}
		
		// convert ScoredSense to WordSense, discard score
		for (ScoredSense wordSense : scoredSenses) {
			rankedSenses.add(wordSense.getSense());
		}
		
		return rankedSenses;
	}
	
	List<ScoredSense> rankSensesUsingLesk() {
		
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
	
	List<ScoredSense> rankSensesUsingStochasticHypernymDistance() {
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
		
		for (CASSWordSense targetSense : targetSenses) {
			int senseScore = 0;
		
			for (String contextWord : context) {
				
				// for each sense of the current context word, find the sense with the minimum distance to the current target sense
				Set<CASSWordSense> contextWordSenses = lTool.getSenses(contextWord);
				
				int bestScore = 0;
				for (CASSWordSense contextWordSense : contextWordSenses) {
					
					int currentScore = getHypernymDistanceScore(targetSense, contextWordSense);
					if (currentScore < bestScore) {
						bestScore = currentScore;
					}
				}
				senseScore += bestScore;
			}
			scoredSenses.add(new ScoredSense(targetSense, senseScore));
		}
		
		// sort in ascending order
		Collections.sort(scoredSenses);
		
		return scoredSenses;
	}
	
	List<ScoredSense> rankSensesUsingTagFrequency() {
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
				
		for (CASSWordSense sense : targetSenses) {
			scoredSenses.add(new ScoredSense(sense, sense.getTagFrequency()));
		}

		Collections.sort(scoredSenses);
		Collections.reverse(scoredSenses);
		
		return scoredSenses;
	}
	
	List<ScoredSense> rankSensesRandomly() {
		Random rand = new Random();
		
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
		
		for (CASSWordSense sense : targetSenses) {
			scoredSenses.add(new ScoredSense(sense, rand.nextInt()));
		}

		Collections.sort(scoredSenses);
		Collections.reverse(scoredSenses);
		
		return scoredSenses;
	}
	
	private int getHypernymDistanceScore(CASSWordSense sense1, CASSWordSense sense2) {
		
		List<CASSWordSense> ancestors1 = getHypernymAncestors(sense1);
		List<CASSWordSense> ancestors2 = getHypernymAncestors(sense2);
		
		Collections.reverse(ancestors1);
		Collections.reverse(ancestors2);
		
		// common ancestor has same ancestor path in both lists
		int depthOfCommonAncestor = 0;
		while(ancestors1.get(depthOfCommonAncestor+1).getId() != ancestors2.get(depthOfCommonAncestor+1).getId()) {
			depthOfCommonAncestor++;
		}
		
		int distanceFromAncestorToS1 = ancestors1.size() - depthOfCommonAncestor;
		int distanceFromAncestorToS2 = ancestors2.size() - depthOfCommonAncestor;
		
		int distanceBetweenSenses = distanceFromAncestorToS1 + distanceFromAncestorToS2;
		
		return distanceBetweenSenses;
	}
	
	private List<CASSWordSense> getHypernymAncestors(CASSWordSense sense) {
		List<CASSWordSense> ancestors = new ArrayList<CASSWordSense>();
		int size, randomIndex;
		while (sense.getId() != "entity") {
			Set<CASSWordSense> hypernyms = lTool.getHypernyms(sense);
			size = hypernyms.size();
			randomIndex = rand.nextInt(size);
			CASSWordSense[] hypernymArray = new CASSWordSense[size];
			hypernyms.toArray(hypernymArray);
			sense = hypernymArray[randomIndex];
			ancestors.add(sense);
		}
		return ancestors;
	}
}