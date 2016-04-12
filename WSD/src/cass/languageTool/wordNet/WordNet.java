package cass.languageTool.wordNet;

import java.util.List;
import java.util.Set;

public interface WordNet {
	Set<String> getSynonyms(WordSense sense);
	List<WordSense> getSenses(String word);
	String getDefinition(WordSense sense);
	
	// TODO: identify full WordNet interface requirements
}
