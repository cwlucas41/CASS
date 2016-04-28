package cass.wsd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cass.languageTool.Language;
import cass.languageTool.LanguageTool;
import cass.testGenerator.TestData;
import cass.testGenerator.TestSentenceGenerator;

public class WSDBenchmark {
	
	public Map<Integer, Map<Integer,Integer>> benchmark(Algorithm algorithm) {
		Iterator<TestData> tsg = new TestSentenceGenerator("semcor3.0");
				
		Map<Integer, Map<Integer,Integer>> bins = new HashMap<Integer, Map<Integer, Integer>>();
		
		while (tsg.hasNext()) {
			TestData ts = tsg.next();
			WSD wsd = new WSD(ts.getLeftContext(), ts.getTarget(), ts.getRightContext(), Language.EN);
			List<ScoredSense> results = wsd.scoreSensesUsing(algorithm);
			
			int numberOfSenses = results.size();
			
			// ensure bin exists
			bins.putIfAbsent(numberOfSenses, new HashMap<Integer, Integer>());
			
			Map<Integer, Integer> bin = bins.get(numberOfSenses);
			
			for (ScoredSense result : results) {
				if (ts.getSenses().contains(result.getSense().getId())) {
					int indexOfCorrectSense = results.indexOf(result);
					Integer currentCount = bin.get(indexOfCorrectSense);
					if (currentCount == null) {
						bin.put(indexOfCorrectSense, 1);
					} else {
						bin.put(indexOfCorrectSense, currentCount++);
					}
				}
			}
		}
		
		return bins;
	}
	
	public void printBenchmark(Algorithm algorithm) {
		Map<Integer, Map<Integer,Integer>> bins = benchmark(algorithm);
		
		List<Integer> keys = new ArrayList<Integer>(bins.keySet());
		Collections.sort(keys);
		for (Integer key : keys) {
			System.out.println(key);
			Map<Integer,Integer> bin = bins.get(key);
			List<Integer> innerKeys = new ArrayList<Integer>(bin.keySet());
			Collections.sort(innerKeys);
			for (Integer innerKey : innerKeys) {
				System.out.println("\t" + innerKey + "\t" + bin.get(innerKey));
			}
		}
	}
	
	public void simpleBenchmark(Algorithm algorithm) {
		Iterator<TestData> tsg = new TestSentenceGenerator("semcor3.0");
		
		int numCorrect = 0;
		int numSentences = 0;
		
		while (tsg.hasNext()) {
			TestData ts = tsg.next();
			
			WSD wsd = new WSD(ts.getLeftContext(), ts.getTarget(), ts.getRightContext(), Language.EN);
			LanguageTool lt = new LanguageTool(Language.EN);
			
			if (!lt.getSenses(ts.getTarget()).isEmpty()) {				
				List<ScoredSense> results = wsd.scoreSensesUsing(algorithm);
				
				if (!results.isEmpty() && ts.getSenses().contains(results.get(0).getSense().getId())) {
					numCorrect++;
				}
	
				numSentences++;
				
				if (numSentences % 50 == 0) {
					System.out.println(numCorrect + " / " + numSentences);
					System.out.println((float) numCorrect / numSentences);
					System.out.println();
				}
			}
		}
		
		System.out.println(numCorrect + " / " + numSentences);
		System.out.println((float) numCorrect / numSentences);
		System.out.println();
	}
}
