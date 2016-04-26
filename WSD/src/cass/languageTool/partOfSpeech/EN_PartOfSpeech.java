package cass.languageTool.partOfSpeech;
import edu.stanford.nlp.simple.Sentence;

/**
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public class EN_PartOfSpeech implements I_PartOfSpeech {
	
	/** 
	 * @param word A string representing a word in a sentence to get the Part of Speech tag associated with it
	 * @return A string of The Part of Speech of word
	 */	
	@Override
	public String getPOStag(String word) {
		String result = new String();
		Sentence sen = new Sentence(word);
		result = sen.posTag(0);		
		return result;
	}

}
