package edu.slu.wsd;

import org.junit.Test;

import edu.slu.wsd.Algorithm;

public class CustomLesk_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.benchmark(Algorithm.CUSTOM_LESK);
	}

}
