package cass.wsd;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import cass.languageTool.Language;
import cass.testGenerator.TestData;
import cass.testGenerator.TestSentenceGenerator;

public class Lesk_Test {

	int numToKeep = 3;
	
	@Test
	public void test() {
		WSD wsd = new WSD("The", "bass", "makes low musical sounds", Language.TEST);
		List<ScoredSense> ranked = wsd.scoreSensesUsing(Algorithm.LESK);
		
		List<String> properID = Arrays.asList("bass0", "bass1");
		List<Integer> properScore = Arrays.asList(3,1);
		
		for (int i = 0; i < ranked.size(); i++) {
			assertEquals(properID.get(i), ranked.get(i).getSense().getId());
			assertEquals(properScore.get(i), (Integer) ranked.get(i).getScore());
			
		}
	}
	
	@Test
	public void systemTest() {
		Iterator<TestData> tsg = new TestSentenceGenerator("semcor3.0");
		
		int numCorrect = 0;
		int numSentences = 0;
		while (tsg.hasNext()) {
			TestData ts = tsg.next();
			WSD wsd = new WSD(ts.getLeftContext(), ts.getTarget(), ts.getRightContext(), Language.EN);
			List<ScoredSense> results = wsd.scoreSensesUsing(Algorithm.LESK);

			Set<String> predicted = new HashSet<String>();
			
			for (int i = 0; i < results.size(); i++) {
				if (i < numToKeep) {
					predicted.add(results.get(i).getSense().getId());
				} else {
					break;
				}
			}
						
			predicted.retainAll(ts.getSenses());
			if (predicted.size() > 0) {
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
