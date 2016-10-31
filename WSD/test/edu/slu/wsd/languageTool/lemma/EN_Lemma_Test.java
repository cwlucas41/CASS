package edu.slu.wsd.languageTool.lemma;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.slu.wsd.languageTool.lemma.EN_Lemma;

public class EN_Lemma_Test {
	
	@Test
	public void test(){
		
		EN_Lemma lem = new EN_Lemma();
		
		String test = new String();
		
		test = lem.lemmatize("had");
		assertEquals(test, "have");
		
		test = lem.lemmatize("eating");
		assertEquals(test, "eat");
		
		test = lem.lemmatize("car");
		assertEquals(test, "car");
		
		test = lem.lemmatize("Fausto");
		assertEquals(test, "Fausto");
		
		test = lem.lemmatize("aren't");
		assertEquals(test, "are");

		
	}

}
