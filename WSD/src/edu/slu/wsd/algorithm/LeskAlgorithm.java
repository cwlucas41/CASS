package edu.slu.wsd.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import edu.slu.wsd.ScoredSense;
import edu.slu.wsd.WSD;
import edu.slu.wsd.languageTool.wordNet.CASSWordSense;

public class LeskAlgorithm implements I_WSDAlgorithm {

	private WSD wsd;
	private Set<String> functionWords;
	
	public LeskAlgorithm(WSD wsd) {
		this.wsd = wsd;
		
		try {
			this.functionWords = getFunctionWords();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Implements the details of Lesk's algorithm.
	 * score is the cardinality of the  intersection of the tokens the the gloss set of each target sense and the tokens of the context.
	 * @param targetSenses - senses that are to be considered
	 * @return sorted List of ScoredSenses scored on token intersection
	 */
	@Override
	public List<ScoredSense> score(Set<CASSWordSense> targetSenses) {
		// context set is set of words in context
		Set<String> contextSet = new HashSet<String>(wsd.getContext());
		
		contextSet.removeAll(functionWords);
		
		List<ScoredSense> scoredSenses= new ArrayList<ScoredSense>();
		
		// for every set of synonyms in the list
		for (CASSWordSense targetSense : targetSenses) {
			Set<String> glossSet = new HashSet<String>();
			
			// add lemmatized tokens of gloss to set
			String definition = wsd.getlTool().getDefinition(targetSense);
			glossSet.addAll(wsd.getlTool().tokenizeAndLemmatize(definition));
			
			// find intersection of sets
			glossSet.retainAll(contextSet);
			
			// score is cardinality of intersection
			int score = glossSet.size();
			
			scoredSenses.add(new ScoredSense(targetSense, score));
		}
		
		// sort in descending order
		Collections.sort(scoredSenses);
		Collections.reverse(scoredSenses);
		
		return scoredSenses;
	}
	
	private Set<String> getFunctionWords() throws FileNotFoundException {
		Scanner s = new Scanner(new File("/usr/lib/cass/textResources/functionWords.txt"));
		Set<String> words = new HashSet<String>();
		while (s.hasNext()) {
			words.add(wsd.getlTool().lemmatize(s.next()));
		}
		s.close();
		return words;
	}
}