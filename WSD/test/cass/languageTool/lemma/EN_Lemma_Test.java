package cass.languageTool.lemma;

import static org.junit.Assert.*;

import org.junit.Test;

import cass.languageTool.lemma.EN_Lemma;

public class EN_Lemma_Test {
	
	@Test
	public void test(){
		
		EN_Lemma lem = new EN_Lemma();
		
		String test = new String();
		
		test = lem.Lemmatize("had");
		assertEquals(test, "have");
		
		test = lem.Lemmatize("eating");
		assertEquals(test, "eat");
		
		test = lem.Lemmatize("car");
		assertEquals(test, "car");
		
		test = lem.Lemmatize("Fausto");
		assertEquals(test, "Fausto");
		
		test = lem.Lemmatize("aren't");
		assertEquals(test, "are");

		
	}

}
