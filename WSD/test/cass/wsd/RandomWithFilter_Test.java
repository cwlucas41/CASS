package cass.wsd;

import org.junit.Test;

public class RandomWithFilter_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.simpleBenchmark(Algorithm.RANDOM_WITH_FILTER);
	}

}
