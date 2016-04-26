package cass.wsd;

import org.junit.Test;

public class Frequency_Test {
	
	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.simpleBenchmark(Algorithm.FREQUENCY);
	}
}
