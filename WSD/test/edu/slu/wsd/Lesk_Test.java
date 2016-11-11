package edu.slu.wsd;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.slu.wsd.Algorithm;
import edu.slu.wsd.ScoredSense;
import edu.slu.wsd.WSD;
import edu.slu.wsd.languageTool.Language;

public class Lesk_Test {
	
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
		WSDBenchmark test = new WSDBenchmark();
		test.benchmark(Algorithm.LESK);
	}
}
