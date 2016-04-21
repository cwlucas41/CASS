package cass.testGenerator;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.Iterator;

import org.junit.Test;

public class TestSentenceGeneratorTester {

	Iterator<TestSentence> testSentences;
	
	public TestSentenceGeneratorTester() throws MalformedURLException {
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
