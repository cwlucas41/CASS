package edu.slu.wsd.languageTool.wordNet;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.slu.wsd.languageTool.Language;
import edu.slu.wsd.languageTool.LanguageTool;
import edu.slu.wsd.languageTool.wordNet.CASSWordSense;
import edu.slu.wsd.languageTool.wordNet.EN_WordNet;
import edu.slu.wsd.languageTool.wordNet.I_WordNet;

public class EN_WordNet_Test {

	I_WordNet wn = new EN_WordNet();
	String testString = "stab";
	Set<CASSWordSense> senses = wn.getSenses(testString, 'N');
	
	private LanguageTool lTool;
	
	@Test
	public void getSensesTest() {
		if (senses.size() == 0) {assertNotNull(null);}
		System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\nSENSES\n-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		for (CASSWordSense sense : senses) {
			assertNotNull(sense);
			assertNotNull(sense.getId());
			assertNotNull(sense.getPOS());
			assertNotNull(sense.getTarget());
			System.out.println(sense.getId());
		}
	}

	@Test
	public void getDefinitionTest() {
		if (senses.size() == 0) {assertNotNull(null);}
		System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\nGLOSSES\n-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		for (CASSWordSense sense : senses) {
			String gloss = wn.getDefinition(sense);
			assertNotNull(gloss);
			System.out.println(gloss);
		}
	}
	
	@Test
	public void getSynonymsTest() {
		lTool = new LanguageTool(Language.EN);
		if (senses.size() == 0) {assertNotNull(null);}
		System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\nSYNONYMS\n-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		for (CASSWordSense sense : senses) {
			Set<String> synonyms = wn.getSynonyms(sense);
			assertNotNull(synonyms);
			System.out.println(synonyms);
			System.out.println("----------------");
			for (String word : synonyms) {
				char toPrint = lTool.getPOStag("This is a test string to ", word, " you in the heart.");
				assertNotNull(toPrint);
				if (toPrint != '\0') {
					System.out.println(toPrint);
					System.out.println(" ");
				}
			}
			System.out.println("----------------");
		}
	}
	
	@Test
	public void getHypernymsTest() {
		if (senses.size() == 0) {assertNotNull(null);}
		System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\nHYPERNYMNS\n-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		for (CASSWordSense sense : senses) {
			Set<CASSWordSense> hypernyms = wn.getHypernyms(sense);
			assertNotNull(hypernyms);
			for (CASSWordSense hypernym : hypernyms) {
				assertNotNull(hypernym.getTarget());
				System.out.println(hypernym.getTarget());
			}
		}
	}
	
	@Test
	public void exampleHypernymsTest() {
		Set<CASSWordSense> senses = wn.getSenses("basketball", 'N');
		
		Set<String> actualHypernymSenseKeys = new HashSet<String>();
		actualHypernymSenseKeys.add("ball%1:06:01::");
		actualHypernymSenseKeys.add("basketball_equipment%1:06:00::");

		
		CASSWordSense chosenSense = null;
		for (CASSWordSense sense : senses) {
			if (sense.getId().equals("basketball%1:06:00::")) {
				chosenSense = sense;
				break;
			}
		}
		
		assertNotNull(chosenSense);
		
		Set<CASSWordSense> hypernyms = wn.getHypernyms(chosenSense);
		for (CASSWordSense hypernym : hypernyms) {
			assertTrue(actualHypernymSenseKeys.contains(hypernym.getId()));
		}
	}
}
