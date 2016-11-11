package edu.slu.wsd;

import edu.slu.wsd.languageTool.wordNet.CASSWordSense;

/**
 * Class that wraps up a word sense and a score
 * @author cwlucas41
 *
 */
public class ScoredSense implements Comparable<ScoredSense> {
	private CASSWordSense sense;
	private int score;
	public ScoredSense(CASSWordSense sense, int score) {
		this.sense = sense;
		this.score = score;
	}
	
	@Override
	public int compareTo(ScoredSense o) {
		if (Integer.compare(getScore(), o.getScore()) == 0)
			return getSense().getId().compareTo(o.getSense().getId());
		else
			return Integer.compare(getScore(), o.getScore());
	}
	
	public CASSWordSense getSense() {
		return sense;
	}
	public int getScore() {
		return score;
	}	
}
