package cass.languageTool.wordNet;

import java.util.ArrayList;
import java.util.List;

public class CASSWordSense {
	
	private List<String> synsetIDs;
	private String target;
	private String part_of_speech;
	private int senseNumber;
	
	public CASSWordSense(String target, String id, String pos, int senseNumber) {
		this.target = target;
		this.synsetIDs = new ArrayList<String>();
		synsetIDs.add(id);
		this.part_of_speech = pos;
		this.senseNumber = senseNumber;
	}
	
	public CASSWordSense(String target, List<String> ids, String pos, int senseNumber) {
		this.target = target;
		this.synsetIDs = ids;
		this.part_of_speech = pos;
		this.senseNumber = senseNumber;
	}
	
	public void addSynsetID(String id) {
		synsetIDs.add(id);
	}

	public String getTarget() {
		return target;
	}
	
	public String getPOS() {
		return part_of_speech;
	}

	public List<String> getSynsetIDs() {
		return synsetIDs;
	}

	public int getSenseNumber() {
		return senseNumber;
	}
}
