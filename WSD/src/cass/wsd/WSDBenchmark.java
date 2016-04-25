package cass.wsd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cass.languageTool.Language;
import cass.testGenerator.TestData;
import cass.testGenerator.TestSentenceGenerator;

public class WSDBenchmark {
	
	public void benchmark(Algorithm algorithm) {
		Iterator<TestData> tsg = new TestSentenceGenerator("semcor3.0");
				
		Map<Integer,HashMap<Integer,Integer>> bins = new HashMap<Integer, HashMap<Integer, Integer>>();
		
		int numCorrect = 0;
		int numSentences = 0;
		
		while (tsg.hasNext()) {
			TestData ts = tsg.next();
			WSD wsd = new WSD(ts.getLeftContext(), ts.getTarget(), ts.getRightContext(), Language.EN);
			List<ScoredSense> results = wsd.scoreSensesUsing(algorithm);
			
			int numberOfSenses = results.size();
			
			if (!results.isEmpty() && ts.getSenses().contains(results.get(0).getSense().getId())) {
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
