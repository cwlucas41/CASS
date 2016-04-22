package cass.languageTool.wordNet;

import java.util.Set;

public interface I_WordNet {
	Set<String> getSynonyms(CASSWordSense sense);
	Set<CASSWordSense> getSenses(String word);
	String getDefinition(CASSWordSense sense);
	Set<CASSWordSense> getHypernyms(CASSWordSense sense);
	
	// TODO: identify full WordNet interface requirements
}
