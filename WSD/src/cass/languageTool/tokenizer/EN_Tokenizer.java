package cass.languageTool.tokenizer;
import java.util.ArrayList;

import edu.stanford.nlp.simple.*;

public class EN_Tokenizer implements I_Tokenizer{

	@Override
	public ArrayList<String> tokenize(String body) {
		ArrayList<String> result = new ArrayList();
		Document doc = new Document(body);
		for (Sentence sen : doc.sentences()){
			for(int i =0; i < sen.length(); i++){
				result.add(sen.word(i));				
			}
			
		}
		
		return result;
	}

}
