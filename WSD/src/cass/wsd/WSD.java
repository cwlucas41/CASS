package cass.wsd;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import cass.languageTool.*;
import cass.languageTool.wordNet.WordSense;

public class WSD {
	
	private LanguageTool lTool;
	private List<String> context;
	private String target;
	
	public WSD(String leftContext, String target, String rightContext, Language language) {
		lTool = new LanguageTool(language);
		
		this.target = target;
		
		context = new ArrayList<String>();
		context.addAll(lTool.tokenizeAndLemmatize(leftContext));
		context.addAll(lTool.tokenizeAndLemmatize(rightContext));
	}
	
	public List<ScoredSense> rankSynsetsUsing(Algorithm algorithm) {
		switch (algorithm) {
		case LESK:
			return rankSynsetsUsingLesk();

		default:
			// TODO throw proper exception
			return null;
		}
	}
	
	private List<ScoredSense> rankSynsetsUsingLesk() {
		List<WordSense> senses = lTool.getSenses(target);
		
		// context set is set of words in context
		Set<String> contextSet = new HashSet<String>(context);
		
		Set<String> glossSet = new HashSet<String>();
		List<ScoredSense> scoredSynsets= new ArrayList<ScoredSense>();
		
		// for every set of synonyms in the list
		for (WordSense sense : senses) {
			// clear and add lemmatized tokens of gloss to set
			glossSet.clear();
			String definition = lTool.getDefinition(sense);
			glossSet.addAll(lTool.tokenizeAndLemmatize(definition));
			
			// find intersection of sets
			glossSet.retainAll(contextSet);
			
			// score is cardinality of intersection
			int score = glossSet.size();
			
			scoredSynsets.add(new ScoredSense(sense, score));
		}
		
		// sort in descending order
		Collections.sort(scoredSynsets);
		Collections.reverse(scoredSynsets);
		
		return scoredSynsets;
	}
}
