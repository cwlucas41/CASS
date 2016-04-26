package cass.wsd;

import org.junit.Test;

public class Hypernym_Test {
	
	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.simpleBenchmark(Algorithm.STOCHASTIC_GRAPH);
	}
}
