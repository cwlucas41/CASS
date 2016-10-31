package edu.slu.cass;

import org.junit.Test;

public class Cass_Test {

	@Test
	public void test() {
			Cass s= new Cass("Be sure that he took so little hurt from the evil, and escaped in the", "end", "because he began his ownership of the Ring so", "English");
			String result = s.getSynonyms("Lesk");
			System.out.println(result);
    }
	
}
