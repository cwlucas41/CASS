package cass.wsd;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cass.languageTool.Language;
import cass.languageTool.lematizer.EN_Lemma;

public class EN_Lemma_Test {
	
	@Test
	public static void test(){
		boolean passed = true;
		
		EN_Lemma lem = new EN_Lemma();
		
		String test = new String();
		
		test = lem.Lemmatize("had");
		if(!test.equals("have")){
			System.out.println("Error with word " + test);
			passed = false;
		}
		
		test = lem.Lemmatize("eating");
		if(!test.equals("eat")){
			System.out.println("Error with word " + test);
			passed = false;
		}
		
		test = lem.Lemmatize("car");
		if(!test.equals("car")){
			System.out.println("Error with word " + test);
			passed = false;
		}
		
		test = lem.Lemmatize("Fausto");
		if(!test.equals("Fausto")){
			System.out.println("Error with word " + test);
			passed = false;
		}
		
		if(passed){
			System.out.println("All tests passed successfully");
			
		}
		
	}

}
