package cass.wsd;

import org.junit.Test;

public class LeskWithFilter_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.benchmark(Algorithm.LESK_WITH_FILTER);
	}

}
