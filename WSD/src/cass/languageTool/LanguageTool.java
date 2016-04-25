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

	public int getHypernymDistanceScore(CASSWordSense sense1, CASSWordSense sense2) {
		
		List<CASSWordSense> ancestors1 = getHypernymAncestors(sense1);
		List<CASSWordSense> ancestors2 = getHypernymAncestors(sense2);
		
		Collections.reverse(ancestors1);
		Collections.reverse(ancestors2);
		
		// common ancestor has same ancestor path in both lists
		int depthOfCommonAncestor = 0;
		while(ancestors1.get(depthOfCommonAncestor+1).getId() != ancestors2.get(depthOfCommonAncestor+1).getId()) {
			depthOfCommonAncestor++;
		}
		
		int distanceFromAncestorToS1 = ancestors1.size() - depthOfCommonAncestor;
		int distanceFromAncestorToS2 = ancestors2.size() - depthOfCommonAncestor;
		
		int distanceBetweenSenses = distanceFromAncestorToS1 + distanceFromAncestorToS2;
		
		return distanceBetweenSenses;
	}
	
	private List<CASSWordSense> getHypernymAncestors(CASSWordSense sense) {
		List<CASSWordSense> ancestors = new ArrayList<CASSWordSense>();
		int size, randomIndex;
		while (sense.getId() != "entity") {
			Set<CASSWordSense> hypernyms = getHypernyms(sense);
			size = hypernyms.size();
			randomIndex = rand.nextInt(size);
			CASSWordSense[] hypernymArray = new CASSWordSense[size];
			hypernyms.toArray(hypernymArray);
			sense = hypernymArray[randomIndex];
			ancestors.add(sense);
		}
		return ancestors;
	}	
}
