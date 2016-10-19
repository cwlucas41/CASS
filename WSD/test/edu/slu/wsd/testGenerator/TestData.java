package edu.slu.wsd.testGenerator;

import java.util.List;

public class TestData {
	
	private String leftContext;
	private String target;
	private List<String> senses;
	private String rightContext;
	
	public TestData(String leftContext, String target, List<String> senses, String rightContext) {
		super();
		this.leftContext = leftContext;
		this.target = target;
		this.senses = senses;
		this.rightContext = rightContext;
	}
	
	public String getLeftContext() {
		return leftContext;
	}
	public String getTarget() {
		return target;
	}
	public List<String> getSenses() {
		return senses;
	}
	public String getRightContext() {
		return rightContext;
	}
	
	@Override
	public String toString() {
		return leftContext + "\n" + target + "\t" + senses + "\n" + rightContext + "\n";
	}
}
