package edu.slu.wsd;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.slu.wsd.languageTool.Language;

public class WSD_Test {

	@Test
	public void test() {
		WSD s = new WSD("Be sure that he took so little hurt from the evil, and escaped in the", "End", "because he began his ownership of the Ring so", Language.EN);
		assertTrue(s.getTarget().equals(s.getTarget().toLowerCase()));
	}

}
