package cass.wsd;

import cass.languageTool.wordNet.CASSWordSense;

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
		return Integer.compare(getScore(), o.getScore());
	}
	
	public CASSWordSense getSense() {
		return sense;
	}
	public int getScore() {
		return score;
	}	
}
