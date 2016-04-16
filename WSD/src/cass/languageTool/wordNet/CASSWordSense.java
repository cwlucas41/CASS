package cass.languageTool.wordNet;

public class CASSWordSense {
	
	private String full_id;
	private String target;
	
	public CASSWordSense(String target, String full_id) {
		this.target = target;
		this.full_id = id;
	}

	public String getTarget() {
		return target;
	}
	
	public String getId() {
		return full_id;
	}
}
