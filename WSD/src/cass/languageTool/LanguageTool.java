package cass.languageTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cass.languageTool.lematizer.*;
import cass.languageTool.tokenizer.*;
import cass.languageTool.wordNet.*;

import java.util.ListIterator;

public class LanguageTool implements Lemmatizer, Tokenizer, WordNet {
	private WordNet wordNet;
	private Tokenizer tokenizer;
	private Lemmatizer lemmatizer;
	
	public LanguageTool(Language language) {
		switch (language) {
		case EN:
			wordNet = new EnWordNet();
			tokenizer = new EnTokenizer();
			lemmatizer = new EnLemmatizer();
			break;

		default:
			break;
		}
	}

	public String lemmatize(String string) {
		return lemmatizer.lemmatize(string);
	}

	public List<String> tokenize(String string) {
		return tokenizer.tokenize(string);
	}
	
	public List<String> tokenizeAndLemmatize(String string) {
		List<String> tokenized = tokenize(string);
		List<String> tokenizedAndLemmatized = new ArrayList<String>();
		
		ListIterator<String> iter = tokenized.listIterator();
		while (iter.hasNext()) {
			tokenizedAndLemmatized.add(lemmatize(iter.next()));
		}
		return tokenizedAndLemmatized;
	}

	@Override
	public Set<String> getSynonyms(WordSense sense) {
		return wordNet.getSynonyms(sense);
	}

	@Override
	public List<WordSense> getSenses(String word) {
		return wordNet.getSenses(word);
	}

	@Override
	public String getDefinition(WordSense sense) {
		return wordNet.getDefinition(sense);
	}
		
}
