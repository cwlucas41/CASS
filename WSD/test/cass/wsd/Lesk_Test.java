package cass.wsd;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import cass.languageTool.Language;
import cass.testGenerator.TestSentence;
import cass.testGenerator.TestSentenceGenerator;

public class Lesk_Test {

	@Test
	public void test() {
		WSD wsd = new WSD("The", "bass", "makes low musical sounds", Language.TEST);
		List<ScoredSense> ranked = wsd.rankSensesUsingLesk();
		
		List<String> properID = Arrays.asList("bass0", "bass1");
		List<Integer> properScore = Arrays.asList(3,1);
		
		for (int i = 0; i < ranked.size(); i++) {
			assertEquals(properID.get(i), ranked.get(i).getSense().getId());
			assertEquals(properScore.get(i), (Integer) ranked.get(i).getScore());
			
		}
	}
	
	@Test
	public void systemTest() throws MalformedURLException {
		Iterator<TestSentence> tsg = new TestSentenceGenerator("semcor3.0");
		TestSentence ts = tsg.next();
		WSD wsd = new WSD(ts.getLeftContext(), ts.getTarget(), ts.getRightContext(), Language.EN);
		List<ScoredSense> results = wsd.rankSensesUsingLesk();
		System.out.println(ts.getTarget());
		System.out.println(ts.getSenses());
		System.out.println();
		System.out.println(results);
		for (ScoredSense sense : results) {
			System.out.println(sense.getSense().getId());
		}
		
	}
}
