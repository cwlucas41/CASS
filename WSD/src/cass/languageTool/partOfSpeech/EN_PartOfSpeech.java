package cass.languageTool.partOfSpeech;

import edu.stanford.nlp.simple.Sentence;

/**
 * English Part of Speech tool, uses Stanford corenlp
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public class EN_PartOfSpeech implements I_PartOfSpeech {
	
	@Override
	public String getPOStag(String word) {
		String result = new String();
		Sentence sen = new Sentence(word);
		result = sen.posTag(0);		
		return result;
	}

}
