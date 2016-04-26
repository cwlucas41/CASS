package cass.languageTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import cass.languageTool.lemma.*;
import cass.languageTool.partOfSpeech.EN_PartOfSpeech;
import cass.languageTool.partOfSpeech.I_PartOfSpeech;
import cass.languageTool.tokenizer.*;
import cass.languageTool.wordNet.*;

import java.util.ListIterator;
import java.util.Random;

public class LanguageTool implements I_Lemma, I_Tokenizer, I_WordNet, I_PartOfSpeech {
	private I_WordNet wordNet;
	private I_Tokenizer tokenizer;
	private I_Lemma lemmatizer;
	private I_PartOfSpeech pos;
	
	private Random rand = new Random();

	
	public LanguageTool(Language language) {
		switch (language) {
		case EN:
			wordNet = new EN_WordNet();
			tokenizer = new EN_Tokenizer();
			lemmatizer = new EN_Lemma();
			pos = new EN_PartOfSpeech();
			break;
			
		case TEST:
			wordNet = new TEST_WordNet();
			tokenizer = new TEST_Tokenizer();
			lemmatizer = new TEST_Lemmatizer();
			break;

		default:
			break;
		}
	}

	public String Lemmatize(String string) {
		return lemmatizer.Lemmatize(string);
	}

	public List<String> tokenize(String string) {
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
	public Set<String> getSynonyms(CASSWordSense sense) {
		return wordNet.getSynonyms(sense);
	}

	@Override
	public Set<CASSWordSense> getSenses(String word) {
		return wordNet.getSenses(word);
	}

	@Override
	public String getDefinition(CASSWordSense sense) {
		return wordNet.getDefinition(sense);
	}

	@Override
	public Set<CASSWordSense> getHypernyms(CASSWordSense sense) {
		return wordNet.getHypernyms(sense);
	}

	@Override
	public String getPOStag(String word) {
		return pos.getPOStag(word);
	}

	public Integer getHypernymDistanceScore(List<CASSWordSense> ancestors1, List<CASSWordSense> ancestors2) {
		
		if ((ancestors1 == null) || (ancestors2 == null) || ancestors1.isEmpty() || ancestors2.isEmpty()) {
			return null;
		}
		
		Collections.reverse(ancestors1);
		Collections.reverse(ancestors2);
		
		// common ancestor has same ancestor path in both lists
		int depthOfCommonAncestor = 0;
		
		int lengthOfShorterChain = Math.min(ancestors1.size(), ancestors2.size());
		
		// make sure roots are the same
		if (!ancestors1.get(0).getId().equals(ancestors2.get(0).getId())) {
			return null;
		}
		
		for (int i = 0; i < lengthOfShorterChain; i++) {
			if (ancestors1.get(i).getId().equals(ancestors2.get(i).getId())) {
				depthOfCommonAncestor++;
			}
		}
		
		int distanceFromAncestorToS1 = ancestors1.size() - depthOfCommonAncestor;
		int distanceFromAncestorToS2 = ancestors2.size() - depthOfCommonAncestor;
		
		int distanceBetweenSenses = distanceFromAncestorToS1 + distanceFromAncestorToS2;
		
		return distanceBetweenSenses;
	}
	
	public List<CASSWordSense> getHypernymAncestors(CASSWordSense sense) {
		List<CASSWordSense> ancestors = new ArrayList<CASSWordSense>();
		
		Set<CASSWordSense> hypernyms = getHypernyms(sense);
		
		while (!hypernyms.isEmpty()) {
			int size = hypernyms.size();
			int randomIndex = rand.nextInt(size);
			List<CASSWordSense> hypernymList = new ArrayList<CASSWordSense>(hypernyms);
			sense = hypernymList.get(randomIndex);
			ancestors.add(sense);
			hypernyms = getHypernyms(sense);
		}
		
		return ancestors;
	}	
}
