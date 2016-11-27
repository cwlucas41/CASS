package edu.slu.wsd.languageTool.partOfSpeech;

/**
 * Interface for all part of speech taggers
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public interface I_PartOfSpeech {
	
	/** 
	 * gets the part of speech tag of a word
	 * @param word A string representing a word in a sentence to get the Part of Speech tag associated with it
	 * @return A string of The Part of Speech of word
	 */			
	public String getPOStag(String leftContext, String target, String rightContext);

}
