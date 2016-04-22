package cass.languageTool.wordNet;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class EN_WordNet_Test {

	I_WordNet wn = new EN_WordNet();
	
	@Test
	public void getSensesTest() {
		Set<CASSWordSense> senses = wn.getSenses("basketball");
		for (CASSWordSense sense : senses) {
			assertNotNull(sense);
			System.out.println(sense.getId());
			System.out.println(sense.getPOS());
		}
	}

	@Test
	public void getDefinitionTest() {
		Set<CASSWordSense> senses = wn.getSenses("basketball");
		for (CASSWordSense sense : senses) {
			String gloss = wn.getDefinition(sense);
			assertNotNull(gloss);
			System.out.println(gloss);
		}
	}
	
}
