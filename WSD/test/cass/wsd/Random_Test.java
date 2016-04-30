package cass.wsd;

import org.junit.Test;


public class Random_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.simpleBenchmark(Algorithm.RANDOM, 0);
	}

}
