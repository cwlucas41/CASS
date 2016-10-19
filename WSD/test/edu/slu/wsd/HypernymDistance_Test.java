package edu.slu.wsd;

import org.junit.Test;

import edu.slu.wsd.Algorithm;

public class HypernymDistance_Test {
	
	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.benchmark(Algorithm.HYPERNYM_DISTANCE);
	}
}
