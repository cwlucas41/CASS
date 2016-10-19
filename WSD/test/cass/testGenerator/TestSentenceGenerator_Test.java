package cass.testGenerator;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class TestSentenceGenerator_Test {
	
	@Test
	public void fieldsAreValid() {
		Iterator<TestData> testSentences = new TestSentenceGenerator();
		while (testSentences.hasNext()) {
			TestData testSentence = testSentences.next();
			assertNotNull(testSentence.getLeftContext());
			assertNotNull(testSentence.getRightContext());
			assertNotNull(testSentence.getTarget());
			assertNotNull(testSentence.getSenses());
			assertTrue(testSentence.getSenses().size() >= 1);
		}
	}
	
	@Test
	public void testFirstSentence() {
		Iterator<TestData> testSentences = new TestSentenceGenerator();
		TestData testSentence = testSentences.next();
		System.out.println(testSentence);
	}

}
