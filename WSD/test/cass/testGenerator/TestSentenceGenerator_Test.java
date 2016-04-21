package cass.testGenerator;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.Iterator;

import org.junit.Test;

public class TestSentenceGenerator_Test {

	Iterator<TestSentence> testSentences;
	
	public TestSentenceGenerator_Test() throws MalformedURLException {
		testSentences = new TestSentenceGenerator("semcor3.0");
	}
	
	@Test
	public void allTargetsAreTagged() {
		while (testSentences.hasNext()) {
			TestSentence testSentence = testSentences.next();
			assertTrue(testSentence.getSenses().size() > 0);
		}
	}

}
