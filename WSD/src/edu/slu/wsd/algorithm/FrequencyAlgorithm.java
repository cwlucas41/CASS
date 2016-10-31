package edu.slu.wsd.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import edu.slu.wsd.ScoredSense;
import edu.slu.wsd.languageTool.wordNet.CASSWordSense;

public class FrequencyAlgorithm implements I_WSDAlgorithm {
	
	/**
	 * Real baseline algorithm. Implements selection of sense based on sense frequency. The results of this algorithm are the baseline
	 * that the WSD algorithms should beat.
	 * @return sorted List of ScoredSenses scored by frequency distribution of sense
	 */
	@Override
	public List<ScoredSense> score(Set<CASSWordSense> senses) {
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
				
		for (CASSWordSense sense : senses) {
			scoredSenses.add(new ScoredSense(sense, sense.getTagFrequency()));
		}

		Collections.sort(scoredSenses);
		Collections.reverse(scoredSenses);
		
		return scoredSenses;
	}
}
