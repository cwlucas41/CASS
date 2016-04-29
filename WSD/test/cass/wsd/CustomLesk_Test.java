package cass.wsd;

import org.junit.Test;

public class CustomLesk_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.simpleBenchmark(Algorithm.CUSTOM_LESK);
	}

}
