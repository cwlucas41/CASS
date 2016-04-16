package cass.languageTool.wordNet;

import java.util.List;
import java.util.Set;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;

public class EnWordNet implements WordNet {

	private WordNetDatabase database;
	
	public EnWordNet() {
		database = WordNetDatabase.getFileInstance();
	}

	@Override
	public Set<String> getSynonyms(WordSense sense) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CASSWordSense> getSenses(String word) {
		List<CASSWordSense> senses;
		Synset[] mySyns = database.getSynsets(word);
		for (int i = 0; i < mySyns.length; i++) {
			WordSense jawsSense = new WordSense(word, mySyns);
			CASSWordSense ourSense = new CASSWordSense(word, jawsSense.toString());
			senses.add(i, ourSense);
		}
		return senses;
	}

	@Override
	public String getDefinition(CASSWordSense sense) {
        String gloss;
        String id = sense.getId();
        String word = sense.getTarget();
        Synset[] syn = database.getSynsets(word);
        for (int i = 0; i < syn.length; i++) {
        	WordSense tempSense = new WordSense(word, syn[i]);
        	if (id == tempSense.toString()) {
        		gloss = syn[i].getDefinition();
        	}
        }
        
		return gloss;
	}
	
}
