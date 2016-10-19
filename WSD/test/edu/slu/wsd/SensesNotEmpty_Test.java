package edu.slu.wsd;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import edu.slu.wsd.Algorithm;
import edu.slu.wsd.ScoredSense;
import edu.slu.wsd.WSD;
import edu.slu.wsd.languageTool.Language;
import edu.slu.wsd.testGenerator.TestData;
import edu.slu.wsd.testGenerator.TestSentenceGenerator;

public class SensesNotEmpty_Test {

	@Test
	public void systemTest() {
		Iterator<TestData> tsg = new TestSentenceGenerator();
		
		int numCorrect = 0;
		int numSentences = 0;
		
		while (tsg.hasNext()) {
			TestData ts = tsg.next();
			WSD wsd = new WSD(ts.getLeftContext(), ts.getTarget(), ts.getRightContext(), Language.EN);
			List<ScoredSense> results = wsd.scoreSensesUsing(Algorithm.RANDOM, 0);
			
			if (!results.isEmpty()) {
				numCorrect++;
			}
			numSentences++;
			
			if (numSentences % 10 == 0) {
				System.out.println(numCorrect + " / " + numSentences);
				System.out.println((float) numCorrect / numSentences);
				System.out.println();
			}
		}
	}
}
