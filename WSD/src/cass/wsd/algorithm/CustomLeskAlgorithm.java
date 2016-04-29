package cass.wsd.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cass.languageTool.wordNet.CASSWordSense;
import cass.wsd.ScoredSense;
import cass.wsd.WSD;

public class CustomLeskAlgorithm implements I_WSDAlgorithm {

	private WSD wsd;
	private double contextThreshold = 0.1;
	
	public CustomLeskAlgorithm(WSD wsd) {
		this.wsd = wsd;
	}
	
	@Override
	public List<ScoredSense> score(Set<CASSWordSense> senses) {
		List<ScoredSense> scoredSenses = new ArrayList<ScoredSense>();
		
		final List<List<Set<String>>> glossOfSensesOfContextWords = prefetchContextGlosses(wsd.getContext());
		
		// for every sense of the target word
		for (CASSWordSense targetSense : senses) {
			Set<String> targetGlossSet = getGlossSet(targetSense);
			int senseScore = 0;
			
			for (List<Set<String>> glossOfSenses : glossOfSensesOfContextWords) {
				int wordScore = 0;
				for (Set<String> gloss : glossOfSenses) {
					Set<String> glossCopy = new HashSet<String>(gloss);
					glossCopy.retainAll(targetGlossSet);
					int score = glossCopy.size();
					if (score > wordScore) {
						wordScore = score;
					}
				}
				senseScore += wordScore;
			}
			scoredSenses.add(new ScoredSense(targetSense, senseScore));
		}
		
		// sort in descending order
		Collections.sort(scoredSenses);
		Collections.reverse(scoredSenses);
		
		return scoredSenses;
	}
	
	private List<List<Set<String>>> prefetchContextGlosses(List<String> context) {
		List<List<Set<String>>> glossPrefetch = new ArrayList<List<Set<String>>>();
		
		for (String contextWord : context) {
			List<Set<String>> listOfGlosses = new ArrayList<Set<String>>();
			
			Set<CASSWordSense> contextWordSenses = wsd.getlTool().getSenses(contextWord);
			// apply filter so that algorithm runs faster
			contextWordSenses = wsd.filterSensesToFrequencyThreshold(contextWordSenses, contextThreshold);
			for (CASSWordSense contextWordSense : contextWordSenses) {
				listOfGlosses.add(getGlossSet(contextWordSense));
			}
			glossPrefetch.add(listOfGlosses);
		}
		
		return glossPrefetch;
	}
	
	private Set<String> getGlossSet(CASSWordSense wordSense) {
		String gloss = wsd.getlTool().getDefinition(wordSense);
		Set<String> glossTokens = new HashSet<String>();
		glossTokens.addAll(wsd.getlTool().tokenizeAndLemmatize(gloss));
		return glossTokens;
	}

}
