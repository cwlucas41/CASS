package cass.wsd;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import cass.languageTool.*;
import cass.languageTool.wordNet.WordSense;

public class WSD {
	
	private LanguageTool lTool;
	private List<String> context;
	@SuppressWarnings("unused")
	private String target;
	private Random rand = new Random();
	Set<WordSense> targetSenses;
	
	public WSD(String leftContext, String target, String rightContext, Language language) {
		lTool = new LanguageTool(language);
		
		this.target = target;
		
		context = new ArrayList<String>();
		context.addAll(lTool.tokenizeAndLemmatize(leftContext));
		context.addAll(lTool.tokenizeAndLemmatize(rightContext));
		
		targetSenses = lTool.getSenses(target);
	}
	
	public List<WordSense> rankSynsetsUsing(Algorithm algorithm) {
		
		List<ScoredSense> scoredSenses;
		List<WordSense> rankedSenses = new ArrayList<WordSense>();
		
		switch (algorithm) {
		case LESK:
			scoredSenses = rankSensesUsingLesk();
			break;

		case STOCHASTIC_GRAPH:
			scoredSenses = rankSensesUsingStochasticHypernymDistance();
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
		for (WordSense targetSense : targetSenses) {
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
		
		for (WordSense targetSense : targetSenses) {
			int senseScore = 0;
		
			for (String contextWord : context) {
				
				// for each sense of the current context word, find the sense with the minimum distance to the current target sense
				Set<WordSense> contextWordSenses = lTool.getSenses(contextWord);
				
				int bestScore = 0;
				for (WordSense contextWordSense : contextWordSenses) {
					
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
	
	private int getHypernymDistanceScore(WordSense sense1, WordSense sense2) {
		
		List<WordSense> ancestors1 = getHypernymAncestors(sense1);
		List<WordSense> ancestors2 = getHypernymAncestors(sense2);
		
		Collections.reverse(ancestors1);
		Collections.reverse(ancestors2);
		
		// common ancestor has same ancestor path in both lists
		int depthOfCommonAncestor = 0;
		for (int i = 0; i < Math.min(ancestors1.size(), ancestors2.size()); i++) {
			if (ancestors1.get(i) != ancestors2.get(i)) {
				depthOfCommonAncestor = i-1;
			}
		}
		
		int distanceFromAncestorToS1 = ancestors1.size() - depthOfCommonAncestor;
		int distanceFromAncestorToS2 = ancestors2.size() - depthOfCommonAncestor;
		
		int distanceBetweenSenses = distanceFromAncestorToS1 + distanceFromAncestorToS2;
		
		return distanceBetweenSenses;
	}
	
	private List<WordSense> getHypernymAncestors(WordSense sense) {
		List<WordSense> ancestors = new ArrayList<WordSense>();
		int size, randomIndex;
		while (sense.getId() != "entity") {
			Set<WordSense> hypernyms = lTool.getHypernyms(sense);
			size = hypernyms.size();
			randomIndex = rand.nextInt(size);
			WordSense[] hypernymArray = new WordSense[size];
			hypernyms.toArray(hypernymArray);
			sense = hypernymArray[randomIndex];
			ancestors.add(sense);
		}
		return ancestors;
	}
}
























