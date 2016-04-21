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
	public void fieldsAreValid() {
		while (testSentences.hasNext()) {
			TestSentence testSentence = testSentences.next();
			assertNotNull(testSentence.getLeftContext());
			assertNotNull(testSentence.getRightContext());
			assertNotNull(testSentence.getTarget());
			assertNotNull(testSentence.getSenses());
			assertTrue(testSentence.getSenses().size() >= 1);
		}
	}
	
	@Test
	public void testFirstSentence() {
		TestSentence testSentence = testSentences.next();
		System.out.println(testSentence);
	}

}
