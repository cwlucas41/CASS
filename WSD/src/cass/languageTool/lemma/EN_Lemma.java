package cass.languageTool.lemma;

import edu.stanford.nlp.simple.*;

/**
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public class EN_Lemma implements I_Lemma {	
	
	@Override
	public String lemmatize(String word) {
		String result = new String();
		Sentence sen = new Sentence(word);
		result = sen.lemma(0);		
		return result;
	}

}
