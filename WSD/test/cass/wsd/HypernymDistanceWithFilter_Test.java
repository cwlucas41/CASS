package cass.wsd;

import org.junit.Test;

public class HypernymDistanceWithFilter_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.benchmark(Algorithm.HYPERNYM_DISTANCE_WITH_FILTER);
	}

}
