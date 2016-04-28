package cass.wsd;

import org.junit.Test;

public class FilteredLesk_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.simpleBenchmark(Algorithm.LESK_WITH_FILTER);
	}

}
