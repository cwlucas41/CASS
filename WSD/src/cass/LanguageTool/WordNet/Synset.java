package cass.LanguageTool.WordNet;

import java.util.Set;

public class Synset {
	
	private Set<String> synonyms;
	private String definition;
	
	public Synset(Set<String> synonyms, String definition) {
		this.synonyms = synonyms;
		this.definition = definition;
	}

	public Set<String> getSynonyms() {
		return synonyms;
	}

	public String getDefinition() {
		return definition;
	}
}
