package cass.wsd;

import org.junit.Test;


public class Random_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.benchmark(Algorithm.RANDOM);
	}

}
