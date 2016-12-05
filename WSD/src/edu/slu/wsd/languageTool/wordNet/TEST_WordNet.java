package edu.slu.wsd.languageTool.wordNet;

import java.util.HashSet;
import java.util.Set;

public class TEST_WordNet implements I_WordNet {

	int numberOfSenses = 2;
	
	@Override
	public Set<String> getSynonyms(CASSWordSense sense) {
	
		return null;
	}

	@Override
	public Set<CASSWordSense> getSenses(String word, char pos) {
		Set<CASSWordSense> set = new HashSet<>(numberOfSenses);
		for (int i = 0; i < numberOfSenses; i++) {
			set.add(new CASSWordSense(null, word + i, null, 0));
		}
		return set;
	}

	@Override
	public String getDefinition(CASSWordSense sense) {
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
	public Set<CASSWordSense> getHypernyms(CASSWordSense sense) {
		// TODO Auto-generated method stub
		return null;
	}

}
