package cass.wsd.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import cass.languageTool.wordNet.CASSWordSense;
import cass.wsd.ScoredSense;
import cass.wsd.WSD;

public class HypernymDistanceAlgorithm implements I_WSDAlgorithm {

	private WSD wsd;
	
	public HypernymDistanceAlgorithm(WSD wsd) {
		this.wsd = wsd;
	}
	
	 /**
	  * WSD algorithm to score senses based on lowest common hypernym ancestor to senses of context words.
	  * The algorithm assigns a score to a target sense that is the sum of the minimum distances between it and the senses of the context words.
	  * The stochastic part of this algorithm relies on randomly selecting a hypernym in the case there are multiple.
	  * @return sorted List of ScoredSenses scored by hypernym traversals to the senses of each context word.
	  */
	@Override
	public List<ScoredSense> score(Set<CASSWordSense> senses) {
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
				
		for (CASSWordSense targetSense : senses) {
			
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
		
		List<CASSWordSense> targetHypernymChain = wsd.getlTool().getHypernymAncestors(targetSense);
		if ((targetHypernymChain == null) || (targetHypernymChain.isEmpty())) {
			return null;
		}
		
		int senseScore = 0;
		
		// loop over all context words
		for (String contextWord : wsd.getContext()) {
			Set<CASSWordSense> contextWordSenses = wsd.getlTool().getSenses(contextWord);
			int contextWordBestScore = Integer.MAX_VALUE;
			if (!contextWordSenses.isEmpty()) {
				// loop over all senses of context word
				for (CASSWordSense contextWordSense : contextWordSenses) {
					if (contextWordSense.getPOS() == "noun") {
						List<CASSWordSense> contextWordHypernymChain = wsd.getlTool().getHypernymAncestors(contextWordSense);
						Integer score = wsd.getlTool().getHypernymDistanceScore(targetHypernymChain, contextWordHypernymChain);
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
}
