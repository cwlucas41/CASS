package cass.wsd;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import cass.languageTool.Language;
import cass.testGenerator.TestData;
import cass.testGenerator.TestSentenceGenerator;

public class SensesNotEmpty_Test {

	@Test
	public void systemTest() {
		Iterator<TestData> tsg = new TestSentenceGenerator("semcor3.0");
		
		int numCorrect = 0;
		int numSentences = 0;
		
		while (tsg.hasNext()) {
			TestData ts = tsg.next();
			WSD wsd = new WSD(ts.getLeftContext(), ts.getTarget(), ts.getRightContext(), Language.EN);
			List<ScoredSense> results = wsd.scoreSensesUsing(Algorithm.RANDOM);
			
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
