package cass.languageTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cass.languageTool.lematizer.*;
import cass.languageTool.tokenizer.*;
import cass.languageTool.wordNet.*;

import java.util.ListIterator;

public class LanguageTool implements I_Lemma, I_Tokenizer, WordNet {
	private WordNet wordNet;
	private I_Tokenizer tokenizer;
	private I_Lemma lemmatizer;
	
	public LanguageTool(Language language) {
		switch (language) {
		case EN:
			wordNet = new EnWordNet();
			tokenizer = new EN_Tokenizer();
			lemmatizer = new EN_Lemma();
			break;
			
		case TEST:
			wordNet = new TestWordNet();
			tokenizer = new EN_Tokenizer();
			lemmatizer = new EN_Lemma();
			break;

		default:
			break;
		}
	}

	public String Lemmatize(String string) {
		return lemmatizer.Lemmatize(string);
	}

	public ArrayList<String> tokenize(String string) {
		return tokenizer.tokenize(string);
	}
	
	public List<String> tokenizeAndLemmatize(String string) {
		List<String> tokenized = tokenize(string);
		List<String> tokenizedAndLemmatized = new ArrayList<String>();
		
		ListIterator<String> iter = tokenized.listIterator();
		while (iter.hasNext()) {
			tokenizedAndLemmatized.add(Lemmatize(iter.next()));
		}
		return tokenizedAndLemmatized;
	}

	@Override
	public Set<String> getSynonyms(WordSense sense) {
		return wordNet.getSynonyms(sense);
	}

	@Override
	public Set<WordSense> getSenses(String word) {
		return wordNet.getSenses(word);
	}

	@Override
	public String getDefinition(WordSense sense) {
		return wordNet.getDefinition(sense);
	}

	@Override
	public Set<WordSense> getHypernyms(WordSense sense) {
		return wordNet.getHypernyms(sense);
	}

	
		
}
