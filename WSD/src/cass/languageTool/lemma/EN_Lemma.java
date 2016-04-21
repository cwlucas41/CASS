package cass.languageTool.lemma;
import edu.stanford.nlp.simple.*;
public class EN_Lemma implements I_Lemma {
	
	@Override
	//Assumes only one word will be passed into function
	public String Lemmatize(String word) {
		String result = new String();
		Sentence sen = new Sentence(word);
		result = sen.lemma(0);		
		return result;
	}

}
