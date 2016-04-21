package cass.testGenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import edu.mit.jsemcor.detokenize.DefaultDetokenizer;
import edu.mit.jsemcor.element.IContext;
import edu.mit.jsemcor.element.ISentence;
import edu.mit.jsemcor.element.IWordform;
import edu.mit.jsemcor.main.IConcordanceSet;
import edu.mit.jsemcor.main.Semcor;

public class TestSentenceGenerator implements Iterable<TestSentence>, Iterator<TestSentence> {
	
	private IConcordanceSet semcor;
	private Iterator<IContext> contextIter;
	private ListIterator<ISentence> sentenceIter;
	
	private DefaultDetokenizer detokenizer =  new DefaultDetokenizer();
	
	public TestSentenceGenerator(String path) throws MalformedURLException {
		semcor = new Semcor(new URL("file", null, path));
		semcor.open();
		
		contextIter = semcor.iterator();
		
		sentenceIter = contextIter.next().getSentences().listIterator();
	}
	

	@Override
	public Iterator<TestSentence> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return (sentenceIter.hasNext() || contextIter.hasNext());
	}

	@Override
	public TestSentence next() {
		
		ISentence sentence;
		int numberOfTaggedWords;
		
		do {
			sentence = getNextISentence();
			
			numberOfTaggedWords = 0;
			for (IWordform wf : sentence.getWordList()) {
				if (wf.getSemanticTag() != null) {
					numberOfTaggedWords++;
				}
			}
		} while (numberOfTaggedWords == 0);
		
		List<String> leftTokens = new ArrayList<String>();
		String target = null;
		List<String> senses = null;
		List<String> rightTokens = new ArrayList<String>();
		
		boolean targetIsFound = false;
		for (IWordform wf : sentence.getWordList()) {
			
			if (!targetIsFound) {
				if (wf.getSemanticTag() != null) {
					target = concatenateTokens(wf.getConstituentTokens());
					senses = wf.getSemanticTag().getSenseKeys();
					targetIsFound = true;
				} else {
					leftTokens.addAll(wf.getConstituentTokens());
				}
			} else {
				rightTokens.addAll(wf.getConstituentTokens());
			}
		}
		
		String leftContext = concatenateTokens(leftTokens);
		String rightContext = concatenateTokens(rightTokens);
		
		return new TestSentence(leftContext, target, senses, rightContext);
	}
	
	private ISentence getNextISentence() {
		
		ISentence nextSentence = null;
		
		if (!sentenceIter.hasNext() && contextIter.hasNext()) {
			// if there is not another sentence but there is another context, update the context and get new sentenceIter
			sentenceIter = contextIter.next().getSentences().listIterator();
		}
		
		if (sentenceIter.hasNext()) {
			nextSentence = sentenceIter.next();
		}
		
		return nextSentence;
	}
	
	private String concatenateTokens(List<String> tokens) {
		int numTokens = tokens.size();
		String concatenated = "";
		if (numTokens != 0) {
		
			concatenated += tokens.get(0);
		
			if (numTokens > 1) {
				String sep = null;
				for (int j = 1; j < tokens.size(); j++) {
					String token = tokens.get(j);
					
					sep = detokenizer.getSeparatorString(concatenated, token);
					if (sep != null) {
						concatenated += sep;
					}
					concatenated += token;
				}
			}
		}
		return concatenated;
	}
}
