package edu.slu.wsd.algorithm;

import java.util.List;
import java.util.Set;

import edu.slu.wsd.ScoredSense;
import edu.slu.wsd.languageTool.wordNet.CASSWordSense;

public interface I_WSDAlgorithm {
	
	/**
	 * Algorithm that returns a sorted list of ScoredSenses through some WSD algorithm.
	 * @param senses of target word
	 * @return sorted list of scored senses
	 */
	List<ScoredSense> score(Set<CASSWordSense> senses);
}
