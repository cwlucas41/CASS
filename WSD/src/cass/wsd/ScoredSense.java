package cass.wsd;

import cass.languageTool.wordNet.WordSense;

public class ScoredSense implements Comparable<ScoredSense> {
	private WordSense sense;
	private int score;
	public ScoredSense(WordSense sense, int score) {
		this.sense = sense;
		this.score = score;
	}
	@Override
	public int compareTo(ScoredSense o) {
		return Integer.compare(this.score, o.score);
	}
	public WordSense getSense() {
		return sense;
	}
	public int getScore() {
		return score;
	}	
}
