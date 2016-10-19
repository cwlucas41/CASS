package edu.slu.wsd.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import edu.slu.wsd.ScoredSense;
import edu.slu.wsd.languageTool.wordNet.CASSWordSense;

public class RandomAlgorithm implements I_WSDAlgorithm {
	
	/**
	 * Internal details for randomly selecting a sense
	 * @param senses - Set of senses to consider
	 * @return List of senses scored randomly
	 */
	@Override
	public List<ScoredSense> score(Set<CASSWordSense> senses) {
				
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
		
		for (CASSWordSense sense : senses) {
			scoredSenses.add(new ScoredSense(sense, 0));
		}
		
		Collections.shuffle(scoredSenses);
		
		return scoredSenses;
	}
}
