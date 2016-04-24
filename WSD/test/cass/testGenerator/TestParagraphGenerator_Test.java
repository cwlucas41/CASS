package cass.testGenerator;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class TestParagraphGenerator_Test {
	
	@Test
	public void fieldsAreValid() {
		Iterator<TestData> testParagraphs = new TestParagraphGenerator("semcor3.0");
		while (testParagraphs.hasNext()) {
			TestData testSentence = testParagraphs.next();
			assertNotNull(testSentence.getLeftContext());
			assertNotNull(testSentence.getRightContext());
			assertNotNull(testSentence.getTarget());
			assertNotNull(testSentence.getSenses());
			assertTrue(testSentence.getSenses().size() >= 1);
		}
	}
	
	@Test
	public void testFirstSentence() {
		Iterator<TestData> testParagraphs = new TestParagraphGenerator("semcor3.0");
		TestData testParagraph = testParagraphs.next();
		System.out.println(testParagraph);
	}

}
