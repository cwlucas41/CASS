package cass.languageTool.tokenizer;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.simple.*;


/**
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public class EN_Tokenizer implements I_Tokenizer{
	
	/**
	 * @param body A string that represents a body of text to be tokenized.
	 * @return A List of Strings where each element are the tokens of all the sentences in body
	 */
	@Override
	public List<String> tokenize(String body) {
		List<String> result = new ArrayList<String>(); 
		Document doc = new Document(body);
		for (Sentence sen : doc.sentences()){
			for(int i =0; i < sen.length(); i++){
				result.add(sen.word(i));				
			}
			
		}
		
		return result;
	}

}
