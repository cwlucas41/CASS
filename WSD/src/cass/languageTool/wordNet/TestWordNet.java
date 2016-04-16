package cass.languageTool.wordNet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestWordNet implements WordNet {

	int numberOfSenses = 2;
	
	@Override
	public Set<String> getSynonyms(WordSense sense) {
	
		return null;
	}

	@Override
	public List<WordSense> getSenses(String word) {
		List<WordSense> list = new ArrayList<>(numberOfSenses);
		for (int i = 0; i < numberOfSenses; i++) {
			list.add(new WordSense(word + i));
		}
		return list;
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

}
