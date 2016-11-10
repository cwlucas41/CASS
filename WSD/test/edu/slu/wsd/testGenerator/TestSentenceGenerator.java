package edu.slu.wsd.testGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import edu.mit.jsemcor.element.ISentence;
import edu.mit.jsemcor.element.IWordform;

public class TestSentenceGenerator extends TestDataGenerator {
	
	private ListIterator<ISentence> sentenceIter;
	
	public TestSentenceGenerator(long seed) {
		super(seed);
		sentenceIter = getContextIter().next().getSentences().listIterator();
	}

	@Override
	public Iterator<TestData> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return (sentenceIter.hasNext() || getContextIter().hasNext());
	}

	@Override
	public TestData next() {
		
		List<String> leftTokens = new ArrayList<String>();
		String target = null;
		List<String> senses = null;
		List<String> rightTokens = new ArrayList<String>();
		
		// get a sentence with at least on tagged word
		ISentence sentence;
		boolean foundTaggedWord = false;
		do {
			sentence = getNextISentence();
			
			foundTaggedWord = false;
			for (IWordform wf : sentence.getWordList()) {
				if (wf.getSemanticTag() != null) {
					foundTaggedWord = true;
				}
			}
		} while (!foundTaggedWord);
		
		// get all the tagged words
		List<IWordform> taggedWords = new ArrayList<IWordform>();
		for (IWordform wf : sentence.getWordList()) {
			if (wf.getSemanticTag() != null) {
				taggedWords.add(wf);
			}
		}
		
		// get the index of a random tagged word
		int numberOfTaggedWords = taggedWords.size();
		int randTaggedWordIndex = rand.nextInt(numberOfTaggedWords);

		// sort all words into either leftContext, target, or rightContext
		int targetNumber = taggedWords.get(randTaggedWordIndex).getNumber();
		int currentNumber;
		for (IWordform wf : sentence.getWordList()) {
			currentNumber = wf.getNumber();
			if (currentNumber < targetNumber) {
				leftTokens.addAll(wf.getConstituentTokens());
			} else if (targetNumber < currentNumber) {
				rightTokens.addAll(wf.getConstituentTokens());
			} else {
				target = concatenateTokens(wf.getConstituentTokens());
				senses = wf.getSemanticTag().getSenseKeys();
			}
		}
		
		String leftContext = concatenateTokens(leftTokens);
		String rightContext = concatenateTokens(rightTokens);
		
		return new TestData(leftContext, target, senses, rightContext);
	}
	
	private ISentence getNextISentence() {
		
		ISentence nextSentence = null;
		
		if (!sentenceIter.hasNext() && getContextIter().hasNext()) {
			// if there is not another sentence but there is another context, update the context and get new sentenceIter
			sentenceIter = getContextIter().next().getSentences().listIterator();
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

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
}
