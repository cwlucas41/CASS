package cass.wsd.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cass.languageTool.wordNet.CASSWordSense;
import cass.wsd.ScoredSense;
import cass.wsd.WSD;

public class LeskAlgorithm implements I_WSDAlgorithm {

	WSD wsd;
	
	public LeskAlgorithm(WSD wsd) {
		this.wsd = wsd;
	}
	
	/**
	 * Implements the details of Lesk's algorithm.
	 * score is the cardinality of the  intersection of the tokens the the gloss set of each target sense and the tokens of the context.
	 * @param targetSenses - senses that are to be considered
	 * @return sorted List of ScoredSenses scored on token intersection
	 */
	@Override
	public List<ScoredSense> score(Set<CASSWordSense> targetSenses) {
		// context set is set of words in context
				Set<String> contextSet = new HashSet<String>(wsd.getContext());
				
				Set<String> glossSet = new HashSet<String>();
				List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
				
				// for every set of synonyms in the list
				for (CASSWordSense targetSense : targetSenses) {
					// clear and add lemmatized tokens of gloss to set
					glossSet.clear();
					String definition = wsd.getlTool().getDefinition(targetSense);
					glossSet.addAll(wsd.getlTool().tokenizeAndLemmatize(definition));
					
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
}
