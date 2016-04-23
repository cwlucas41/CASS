package cass.languageTool.wordNet;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class EN_WordNet_Test {

	I_WordNet wn = new EN_WordNet();
	String testString = "basketball";
	Set<CASSWordSense> senses = wn.getSenses(testString);
	
	@Test
	public void getSensesTest() {
		for (CASSWordSense sense : senses) {
			assertNotNull(sense);
			assertNotNull(sense.getId());
			assertNotNull(sense.getPOS());
			assertNotNull(sense.getTarget());
		}
	}

	@Test
	public void getDefinitionTest() {
		for (CASSWordSense sense : senses) {
			String gloss = wn.getDefinition(sense);
			assertNotNull(gloss);
		}
	}
	
	@Test
	public void getSynonymsTest() {
		for (CASSWordSense sense : senses) {
			Set<String> synonyms = wn.getSynonyms(sense);
			assertNotNull(synonyms);
//			System.out.println(synonyms);
		}
	}
	
	@Test
	public void getHypernymsTest() {
		for (CASSWordSense sense : senses) {
			Set<CASSWordSense> hypernyms = wn.getHypernyms(sense);
			assertNotNull(hypernyms);
			for (CASSWordSense hypernym : hypernyms) {
				assertNotNull(hypernym.getTarget());
//				System.out.println(hypernym.getTarget());
			}
		}
	}
}
