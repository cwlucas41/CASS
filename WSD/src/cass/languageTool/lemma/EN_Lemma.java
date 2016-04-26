package cass.languageTool.lemma;
import edu.stanford.nlp.simple.*;

/**
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public class EN_Lemma implements I_Lemma {	
	
	/**
	 * @param word A string that represents one word to be lemmatized
	 * @return A string of the lemmatized version of the word
	 */
	public String Lemmatize(String word) {
		String result = new String();
		Sentence sen = new Sentence(word);
		result = sen.lemma(0);		
		return result;
	}

}
