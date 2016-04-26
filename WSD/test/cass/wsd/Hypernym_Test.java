package cass.wsd;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import cass.languageTool.Language;
import cass.languageTool.LanguageTool;
import cass.languageTool.wordNet.CASSWordSense;
import cass.testGenerator.TestData;
import cass.testGenerator.TestSentenceGenerator;
import edu.stanford.nlp.time.JollyDayHolidays.MyXMLManager;

public class Hypernym_Test {
	
	@Test
	public void systemTest() throws MalformedURLException {
		Iterator<TestData> tsg = new TestSentenceGenerator("semcor3.0");
		
		int numCorrect = 0;
		int numSentences = 0;
		
		while (tsg.hasNext()) {
			TestData ts = tsg.next();
			
			
			
			WSD wsd = new WSD(ts.getLeftContext(), ts.getTarget(), ts.getRightContext(), Language.EN);
			LanguageTool lt = new LanguageTool(Language.EN);
			
			if (!lt.getSenses(ts.getTarget()).isEmpty()) {				
				List<ScoredSense> results = wsd.scoreSensesUsing(Algorithm.STOCHASTIC_GRAPH);
				
				if (!results.isEmpty() && ts.getSenses().contains(results.get(0).getSense().getId())) {
					numCorrect++;
				}
	
				numSentences++;
				
				System.out.println(numCorrect + " / " + numSentences);
				System.out.println((float) numCorrect / numSentences);
				System.out.println();
			}
		}
	}
//	
//	@Test
//	public void distanceTest() {
//		CASSWordSense s1 = new CASSWordSense("administration", "administration%1:04:00::", "noun", 0);
//		CASSWordSense s2 = new CASSWordSense("reduce", "reduce%2:30:01::", "verb", 0);
//		
//		LanguageTool lt = new LanguageTool(Language.EN);
//		List<CASSWordSense> chain1 = lt.getHypernymAncestors(s1);
//		List<CASSWordSense> chain2 = lt.getHypernymAncestors(s2);
//		Integer dist = lt.getHypernymDistanceScore(chain1, chain2);
//		System.out.println(dist);
//	}
	
//	@Test
//	public void test() {
//		CASSWordSense sense = new CASSWordSense("musket ball", "musket_ball%1:06:00::", "noun", 0);
//		LanguageTool lt = new LanguageTool(Language.EN);
//		List<CASSWordSense> ancestors = lt.getHypernymAncestors(sense);
//		for (CASSWordSense ancestor : ancestors) {
//			System.out.println(ancestor.getTarget());
//		}
//	}

}
