package edu.slu.wsd.testGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import edu.mit.jsemcor.detokenize.DefaultDetokenizer;
import edu.mit.jsemcor.element.IParagraph;
import edu.mit.jsemcor.element.ISentence;
import edu.mit.jsemcor.element.IWordform;

public class TestParagraphGenerator extends TestDataGenerator {
	
	private ListIterator<IParagraph> paragraphIter;
	
	private Random rand = new Random();
	
	private DefaultDetokenizer detokenizer =  new DefaultDetokenizer();
	
	public TestParagraphGenerator(String path) {
		super(path);
		paragraphIter = getContextIter().next().getParagraphs().listIterator();
	}
	

	@Override
	public Iterator<TestData> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return (paragraphIter.hasNext() || getContextIter().hasNext());
	}

	@Override
	public TestData next() {
		
		List<String> leftTokens = new ArrayList<String>();
		String target = null;
		List<String> senses = null;
		List<String> rightTokens = new ArrayList<String>();
		
		// get a sentence with at least on tagged word
		List<ISentence> sentences;
		boolean foundTaggedWord = false;
		do {
			sentences = getNextIParagraph();
			
			foundTaggedWord = false;
			for (ISentence sentence : sentences) {
				for (IWordform wf : sentence.getWordList()) {
					if (wf.getSemanticTag() != null) {
						foundTaggedWord = true;
					}
				}
			}
		} while (!foundTaggedWord);
		
		// get all the tagged words
		
		List<ArrayList<IWordform>> allTaggedWords = new ArrayList<ArrayList<IWordform>>();
		for (ISentence sentence : sentences) {
			ArrayList<IWordform> taggedWords = new ArrayList<IWordform>();
			for (IWordform wf : sentence.getWordList()) {
				if (wf.getSemanticTag() != null) {
					taggedWords.add(wf);
				}
			}
			allTaggedWords.add(taggedWords);
		}
		
		int numberOfSentences = allTaggedWords.size();
		int randSentenceIndex;
		do {
			randSentenceIndex = rand.nextInt(numberOfSentences);
		} while (allTaggedWords.get(randSentenceIndex).isEmpty());
			
		
		// get the index of a random tagged word
		int numberOfTaggedWords = allTaggedWords.get(randSentenceIndex).size();
		int randTaggedWordIndex = rand.nextInt(numberOfTaggedWords);

		// sort all words into either leftContext, target, or rightContext
		int targetNumber = allTaggedWords.get(randSentenceIndex).get(randTaggedWordIndex).getNumber();
		int currentNumber;
		
		int sentenceBaseIndex = sentences.get(0).getNumber();
		for (ISentence sentence : sentences) {
			if (sentence.getNumber() - sentenceBaseIndex < randSentenceIndex) {
				for (IWordform wf : sentence.getWordList()) {
					leftTokens.addAll(wf.getConstituentTokens());
				}
			} else if (sentence.getNumber() - sentenceBaseIndex > randSentenceIndex) {
				for (IWordform wf : sentence.getWordList()) {
					rightTokens.addAll(wf.getConstituentTokens());
				}			} else {
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
			}
		}
		
		String leftContext = concatenateTokens(leftTokens);
		String rightContext = concatenateTokens(rightTokens);
		
		return new TestData(leftContext, target, senses, rightContext);
	}
	
	private List<ISentence> getNextIParagraph() {
		
		IParagraph nextParagraph = null;
		
		if (!paragraphIter.hasNext() && getContextIter().hasNext()) {
			// if there is not another sentence but there is another context, update the context and get new sentenceIter
			paragraphIter = getContextIter().next().getParagraphs().listIterator();
		}
		
		if (paragraphIter.hasNext()) {
			nextParagraph = paragraphIter.next();
		}
		
		return nextParagraph;
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
