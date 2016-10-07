package cass.wsd;

import org.junit.Test;

public class CustomLesk_Test {

	@Test
	public void systemTest() {
		WSDBenchmark test = new WSDBenchmark();
		test.benchmark(Algorithm.CUSTOM_LESK);
	}

}
