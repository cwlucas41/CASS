package cass.LanguageTool.WordNet;

import java.util.List;

public interface WordNet {
	List<Synset> getSynsets(String word);
	
	// TODO: identify full WordNet interface requirements
}
