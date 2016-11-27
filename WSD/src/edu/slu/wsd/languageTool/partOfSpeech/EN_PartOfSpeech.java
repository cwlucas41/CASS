package edu.slu.wsd.languageTool.partOfSpeech;

import edu.stanford.nlp.simple.Sentence;

/**
 * English Part of Speech tool, uses Stanford corenlp
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public class EN_PartOfSpeech implements I_PartOfSpeech {
	
	@Override
	public String getPOStag(String leftContext, String target, String rightContext) { // any null checks needed?
		String result = new String();
		Sentence frag = new Sentence(leftContext);
		int index = frag.length();
		frag = null; // the above can be done more efficiently
		Sentence sen = new Sentence(leftContext + target + rightContext);
		result = sen.posTag(index);		
		return result;
	}

}
