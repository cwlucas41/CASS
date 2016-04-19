package cass.languageTool.wordNet;

import java.util.HashSet;
import java.util.Set;

public class TestWordNet implements WordNet {

	int numberOfSenses = 2;
	
	@Override
	public Set<String> getSynonyms(WordSense sense) {
	
		return null;
	}

	@Override
	public Set<WordSense> getSenses(String word) {
		Set<WordSense> set = new HashSet<>(numberOfSenses);
		for (int i = 0; i < numberOfSenses; i++) {
			set.add(new WordSense(word + i));
		}
		return set;
	}

	@Override
	public String getDefinition(WordSense sense) {
		String id = sense.getId();
		String gloss = null;
		switch (id) {
		case "bass0":
			gloss = "the lowest part of the musical range";
			break;
			
		case "bass1":
			gloss = "the lean flesh of a saltwater fish of the family Serranidae";

		default:
			break;
		}
		
		return gloss;
	}

	@Override
	public Set<WordSense> getHypernyms(WordSense sense) {
		// TODO Auto-generated method stub
		return null;
	}

}
