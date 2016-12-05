package edu.slu.wsd.languageTool.wordNet;

import java.util.Set;

/**
 * @author TEWatson
 * Interface for accessing WordNet functionality
 */
public interface I_WordNet {
	
	/**
	 * Returns synonyms that correspond to the sense provided in the argument.
	 * <p>
	 * The synonyms are returned as a Set of Strings, not CASSWordSenses, so 
	 * the synonyms returned may themselves represent many different senses.
	 * @param sense CASSWordSense object for which to retrieve synonyms
	 * @return Set of Strings which are the synonyms of the given word sense
	 */
	Set<String> getSynonyms(CASSWordSense sense);
	
	/**
	 * Returns all senses that may use the lemma provided in the argument.
	 * <p>
	 * It is assumed that the word String entered is the lemmatized version 
	 * of the word, or else no senses may be found the this query.
	 * @param word String lemma of the word being queried
	 * @return Set of CASSWordSense objects which share a lemma with the argument
	 */
	Set<CASSWordSense> getSenses(String word, char partOfSpeech);
	
	/**
	 * Returns the definition of the word sense provided in the argument.
	 * @param sense CASSWordSense object for which to retrieve a gloss
	 * @return String representation of the gloss
	 */
	String getDefinition(CASSWordSense sense);
	
	/**
	 * Returns all hypernyms of the argument according to WordNet.
	 * <p>
	 * Depending on the WordNet used, this may or may not return any hypernyms 
	 * for adjectives and adverbs. English WordNet does not return hypernyms for 
	 * adjectives and adverbs.
	 * @param sense CASSWordSense object for which to retreive hypernyms
	 * @return Set of CASSWordSense objects which are hypernyms of the argument
	 */
	Set<CASSWordSense> getHypernyms(CASSWordSense sense);
	
}
