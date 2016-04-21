package cass.wsd;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cass.languageTool.Language;
import cass.languageTool.tokenizer.EN_Tokenizer;

public class EN_Tokenizer_Test {
	@Test
	public static void test(){
		boolean passed = true;
		EN_Tokenizer tok = new EN_Tokenizer();
		List<String> test = null;
		List<String> result = null;
		test.add("The");
		test.add("cow");
		test.add("jumped");
		test.add("over");
		test.add("the");
		test.add("moon");
		result = tok.tokenize("The cow jumped over the moon");
		
		for(int i=0; i<test.size();i++){
			if(!test.get(i).equals(result.get(i))){
				System.out.println("Error at index" + Integer.toString(i));
				System.out.println(test.get(i)+"!="+result.get(i));
				passed = false;
			}
		}
		
		test = null;
		result = null;
		test.add("asjdgasfhjbas");		
		result = tok.tokenize("asjdgasfhjbas");
		for(int i=0; i<test.size();i++){
			if(!test.get(i).equals(result.get(i))){
				System.out.println("Error at index" + Integer.toString(i));
				System.out.println(test.get(i)+"!="+result.get(i));
				passed = false;
			}
		}
		
		test = null;
		result = null;
		test.add("hello");	
		test.add(",");
		test.add("how");
		test.add(".");
		test.add("are");
		test.add("you");
		test.add("?");
		result = tok.tokenize("hello, how. are you?");
		
		for(int i=0; i<test.size();i++){
			if(!test.get(i).equals(result.get(i))){
				System.out.println("Error at index" + Integer.toString(i));
				System.out.println(test.get(i)+"!="+result.get(i));
				passed = false;
			}
		}
		if(passed){
			System.out.println("All test passed successfully");
		}
	 	
	}

}
