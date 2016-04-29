package cass.wsd;

import org.junit.Test;

public class NewLesk_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.simpleBenchmark(Algorithm.NEW_LESK);
	}

}
