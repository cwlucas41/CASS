package cass.wsd.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cass.languageTool.wordNet.CASSWordSense;
import cass.wsd.ScoredSense;
import cass.wsd.WSD;

public class BetterLeskAlgorithm implements I_WSDAlgorithm {

	private WSD wsd;
	private double contextThreshold = 0.1;
	
	public BetterLeskAlgorithm(WSD wsd) {
		this.wsd = wsd;
	}
	
	@Override
	public List<ScoredSense> score(Set<CASSWordSense> senses) {
		List<ScoredSense> scoredSenses = new ArrayList<ScoredSense>();
		
		// for every sense of the target word
		for (CASSWordSense targetSense : senses) {
			Set<String> targetGlossSet = getGlossSet(targetSense);
			int senseScore = 0;
			
			// for every word in the context
			for (String contextWord : wsd.getContext()) {
				// score of best sense of word
				int wordScore = 0;
				
				Set<CASSWordSense> contextWordSenses = wsd.getlTool().getSenses(contextWord);
				// apply filter so that algorithm runs faster
				contextWordSenses = wsd.filterSensesToFrequencyThreshold(contextWordSenses, contextThreshold);
				
				// for every relevent sense of the context word
				for (CASSWordSense contextWordSense : contextWordSenses){
					
					// compute the cardinality of the intersection of the target gloss and context gloss
					Set<String> contextWordGlossSet = getGlossSet(contextWordSense);
					contextWordGlossSet.retainAll(targetGlossSet);
					int score = contextWordGlossSet.size();
					if (score > wordScore) {
						wordScore = score;
					}
				}
				senseScore += wordScore;
			}
			scoredSenses.add(new ScoredSense(targetSense, senseScore));
		}
		
		return scoredSenses;
	}
	
	private Set<String> getGlossSet(CASSWordSense wordSense) {
		String gloss = wsd.getlTool().getDefinition(wordSense);
		Set<String> glossTokens = new HashSet<String>();
		glossTokens.addAll(wsd.getlTool().tokenizeAndLemmatize(gloss));
		return glossTokens;
	}

}
