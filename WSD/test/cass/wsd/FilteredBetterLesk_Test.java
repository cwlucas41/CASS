package cass.wsd;

import org.junit.Test;

public class FilteredBetterLesk_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.simpleBenchmark(Algorithm.BETTER_LESK_WITH_FILTER);
	}

}