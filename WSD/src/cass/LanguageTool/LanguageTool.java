package cass.LanguageTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.ListIterator;

import cass.LanguageTool.Dictionary.*;
import cass.LanguageTool.Lematizer.*;
import cass.LanguageTool.Thesaurus.*;
import cass.LanguageTool.Tokenizer.*;
import cass.LanguageTool.WordNet.*;

public class LanguageTool implements WordNet, Dictionary, Thesaurus, Tokenizer, Lemmatizer{
	private WordNet wordNet;
	private Dictionary dictionary;
	private Thesaurus thesaurus;
	private Tokenizer tokenizer;
	private Lemmatizer lemmatizer;
	
	public LanguageTool(Language language) {
		switch (language) {
		case EN:
			wordNet = new EnWordNet();
			dictionary = new EnDictionary();
			thesaurus = new EnThesaurus();
			tokenizer = new EnTokenizer();
			lemmatizer = new EnLemmatizer();
			break;

		default:
			break;
		}
	}

	@Override
	public String lemmatize(String string) {
		return lemmatizer.lemmatize(string);
	}

	@Override
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
	public Set<String> getSynonyms(String word) {
		return thesaurus.getSynonyms(word);
	}

	@Override
	public String getDefinition(String word) {
		return dictionary.getDefinition(word);
	}

	@Override
	public List<Synset> getSynsets(String word) {
		return wordNet.getSynsets(word);
	}
	
	
}
