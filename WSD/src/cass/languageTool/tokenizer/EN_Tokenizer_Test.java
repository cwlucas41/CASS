package cass.languageTool.tokenizer;
import java.util.ArrayList;

public class EN_Tokenizer_Test {
	public static void main(String[] args){
		boolean passed = true;
		EN_Tokenizer tok = new EN_Tokenizer();
		ArrayList<String> test = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
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
		
		test = new ArrayList<String>();
		result = new ArrayList<String>();
		test.add("asjdgasfhjbas");		
		result = tok.tokenize("asjdgasfhjbas");
		for(int i=0; i<test.size();i++){
			if(!test.get(i).equals(result.get(i))){
				System.out.println("Error at index" + Integer.toString(i));
				System.out.println(test.get(i)+"!="+result.get(i));
				passed = false;
			}
		}
		
		test = new ArrayList<String>();
		result = new ArrayList<String>();
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
