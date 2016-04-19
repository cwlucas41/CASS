package cass.languageTool.wordNet;

import java.util.Set;

public interface WordNet {
	Set<String> getSynonyms(WordSense sense);
	Set<WordSense> getSenses(String word);
	String getDefinition(WordSense sense);
	Set<WordSense> getHypernyms(WordSense sense);
	
	// TODO: identify full WordNet interface requirements
}
