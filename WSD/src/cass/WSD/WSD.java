package cass.WSD;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import cass.LanguageTool.*;
import cass.LanguageTool.WordNet.Synset;

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
	
	public List<Set<String>> rankSynsetsUsing(Algorithm algorithm) {
		switch (algorithm) {
		case LESK_WITH_WORDNET:
			return rankSynsetsUsingLeskAndWordNet();

		default:
			// TODO throw proper exception
			return null;
		}
	}
	
	private List<Set<String>> rankSynsetsUsingLeskAndWordNet() {
		List<Synset> synsets = lTool.getSynsets(target);
		
		// context set is set of words in context
		Set<String> contextSet = new HashSet<String>(context);
		
		Set<String> glossSet = new HashSet<String>();
		List<ScoredSynset> scoredSynsets= new ArrayList<ScoredSynset>();
		
		// for every set of synonyms in the list
		for (Synset synset : synsets) {
			// clear and add lemmatized tokens of gloss to set
			glossSet.clear();
			glossSet.addAll(lTool.tokenizeAndLemmatize(synset.getDefinition()));
			
			// find intersection of sets
			glossSet.retainAll(contextSet);
			
			// score is cardinality of intersection
			int score = glossSet.size();
			
			scoredSynsets.add(new ScoredSynset(synset, score));
		}
		
		// sort in descending order
		Collections.sort(scoredSynsets);
		Collections.reverse(scoredSynsets);
		
		// build list of sets of synonyms sorted by score (score is discarded)
		List<Set<String>> result = new ArrayList<Set<String>>();
		ListIterator<ScoredSynset> iter = scoredSynsets.listIterator();
		while (iter.hasNext()) {
			result.add(iter.next().getSynset().getSynonyms());
		}
		return result;
	}
	
	class ScoredSynset implements Comparable<ScoredSynset> {
		private Synset synset;
		private int score;
		public ScoredSynset(Synset synset, int score) {
			this.synset = synset;
			this.score = score;
		}
		@Override
		public int compareTo(ScoredSynset o) {
			return Integer.compare(this.score, o.score);
		}
		public Synset getSynset() {
			return synset;
		}
		public int getScore() {
			return score;
		}	
	}
}
