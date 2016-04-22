package cass.languageTool.wordNet;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class EN_WordNet_Test {

	I_WordNet wn = new EN_WordNet();
	
	@Test
	public void getSensesTest() {
		Set<CASSWordSense> senses = wn.getSenses("java");
		for (CASSWordSense sense : senses) {
			assertNotNull(sense);
			System.out.println(sense.getId());
		}
	}

}
