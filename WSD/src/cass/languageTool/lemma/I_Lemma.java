package cass.languageTool.lemma;


/**
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public interface I_Lemma {
	/**
	 * @param word A string that represents one word to be lemmatized
	 * @return A string of the lemmatized version of the word
	 */
	public String Lemmatize(String word);
}
