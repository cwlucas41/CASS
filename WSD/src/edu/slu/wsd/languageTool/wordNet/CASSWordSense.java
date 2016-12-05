package edu.slu.wsd.languageTool.wordNet;

public class CASSWordSense {
	
	private String full_id;
	private String target;
	private String part_of_speech;
	private int tagFrequency;
	private boolean isCorrectPOS;
	
	public CASSWordSense(String target, String id, String pos, int tagFrequency) {
		this.target = target;
		this.full_id = id;
		this.part_of_speech = pos;
		this.tagFrequency = tagFrequency;
		this.isCorrectPOS = false;
	}

	public String getTarget() {
		return target;
	}
	
	public String getId() {
		return full_id;
	}
	
	public String getPOS() {
		return part_of_speech;
	}
	
	public Integer getTagFrequency() {
		return tagFrequency;
	}
	
	public void isCorrect(boolean isCorrect) {
		isCorrectPOS = isCorrect;
	}
	
	public boolean isCorrectPOS() {
		return isCorrectPOS;
	}
}
