package cass.wsd;

import java.util.List;
import java.util.ListIterator;
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
	
	public List<WordSense> rankSynsetsUsing(Algorithm algorithm) {
		switch (algorithm) {
		case LESK:
			return rankSynsetsUsingLesk();

		default:
			// TODO throw proper exception
			return null;
		}
	}
	
	private List<WordSense> rankSynsetsUsingLesk() {
		List<WordSense> senses = lTool.getSenses(target);
		
		// context set is set of words in context
		Set<String> contextSet = new HashSet<String>(context);
		
		Set<String> glossSet = new HashSet<String>();
		List<ScoredSense> scoredSynsets= new ArrayList<ScoredSense>();
		
		// for every set of synonyms in the list
		for (WordSense sense : senses) {
			// clear and add lemmatized tokens of gloss to set
			glossSet.clear();
			glossSet.addAll(lTool.tokenizeAndLemmatize(lTool.getDefinition(sense)));
			
			// find intersection of sets
			glossSet.retainAll(contextSet);
			
			// score is cardinality of intersection
			int score = glossSet.size();
			
			scoredSynsets.add(new ScoredSense(sense, score));
		}
		
		// sort in descending order
		Collections.sort(scoredSynsets);
		Collections.reverse(scoredSynsets);
		
		// build list of sets of synonyms sorted by score (score is discarded)
		List<WordSense> result = new ArrayList<WordSense>();
		ListIterator<ScoredSense> iter = scoredSynsets.listIterator();
		while (iter.hasNext()) {
			result.add(iter.next().getSense());
		}
		return result;
	}
	
	class ScoredSense implements Comparable<ScoredSense> {
		private WordSense sense;
		private int score;
		public ScoredSense(WordSense sense, int score) {
			this.sense = sense;
			this.score = score;
		}
		@Override
		public int compareTo(ScoredSense o) {
			return Integer.compare(this.score, o.score);
		}
		public WordSense getSense() {
			return sense;
		}
		public int getScore() {
			return score;
		}	
	}
}
