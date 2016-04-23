package cass.languageTool.wordNet;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class EN_WordNet_Test {

	I_WordNet wn = new EN_WordNet();
	
	String testString = "java";
	
	@Test
	public void getSensesTest() {
		Set<CASSWordSense> senses = wn.getSenses(testString);
		for (CASSWordSense sense : senses) {
			assertNotNull(sense);
			assertNotNull(sense.getId());
			assertNotNull(sense.getPOS());
			assertNotNull(sense.getTarget());
		}
	}

	@Test
	public void getDefinitionTest() {
		Set<CASSWordSense> senses = wn.getSenses(testString);
		for (CASSWordSense sense : senses) {
			String gloss = wn.getDefinition(sense);
			assertNotNull(gloss);
		}
	}	
}
