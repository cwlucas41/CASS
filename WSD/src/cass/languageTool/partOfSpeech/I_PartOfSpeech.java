package cass.languageTool.partOfSpeech;

/**
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public interface I_PartOfSpeech {
	
	/** 
	 * @param word A string representing a word in a sentence to get the Part of Speech tag associated with it
	 * @return A string of The Part of Speech of word
	 */			
	public String getPOStag(String word);

}
