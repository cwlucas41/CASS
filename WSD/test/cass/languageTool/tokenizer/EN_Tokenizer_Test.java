package cass.languageTool.tokenizer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cass.languageTool.tokenizer.EN_Tokenizer;

public class EN_Tokenizer_Test {
	@Test
	public void test(){
		
		EN_Tokenizer tok = new EN_Tokenizer();
		List<String> test = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		test.add("The");
		test.add("cow");
		test.add("jumped");
		test.add("over");
		test.add("the");
		test.add("moon");
		result = tok.tokenize("The cow jumped over the moon");
		
		for(int i=0; i<test.size();i++){
			assertEquals(test.get(i), result.get(i));
		}
		
		test.clear();
		result.clear();
		test.add("asjdgasfhjbas");		
		result = tok.tokenize("asjdgasfhjbas");
		for(int i=0; i<test.size();i++){
			assertEquals(test.get(i), result.get(i));
		}
		
		test.clear();
		result.clear();
		test.add("hello");	
		test.add(",");
		test.add("how");
		test.add(".");
		test.add("are");
		test.add("you");
		test.add("?");
		result = tok.tokenize("hello, how.  are you?");
		
		for(int i=0; i<test.size();i++){
			assertEquals(test.get(i), result.get(i));
		}
	}
}
