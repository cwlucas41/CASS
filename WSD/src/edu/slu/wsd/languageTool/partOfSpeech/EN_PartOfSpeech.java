package edu.slu.wsd.languageTool.partOfSpeech;

import edu.stanford.nlp.simple.Sentence;

/**
 * English Part of Speech tool, uses Stanford corenlp
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public class EN_PartOfSpeech implements I_PartOfSpeech {
	
	@Override
	public char getPOStag(String leftContext, String target, String rightContext, int targetIndex) { // any null checks needed?
		String result = new String();
		Sentence sen = new Sentence(leftContext + " " + target + " " + rightContext);
		//System.out.println(leftContext);
		//System.out.println(target);
		//System.out.println(rightContext);
		//System.out.println("Sentence with index " + index + ": " + sen.toString());
		result = sen.posTag(targetIndex);
		sen = null;
		return result.charAt(0); // part of speech tag is simplified to the first character (noun, verb, adjective, adverb, etc.)
	}

}
