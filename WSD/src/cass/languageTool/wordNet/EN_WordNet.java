package cass.languageTool.wordNet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.Pointer;
import edu.mit.jwi.item.POS;

/**
 * @author TEWatson
 * English implementation of I_WordNet using JWI and WordNet 3.0.
 */
public class EN_WordNet implements I_WordNet {
	
	/**
	 * Stores JWI Dictionary object used to create objects referencing WordNet
	 */
	private IDictionary dict;
	
	/**
	 * Creates the Dictionary object from the included WordNet database file
	 */
	public EN_WordNet() {
		String path = "WNdb-3.0/dict";
		
		URL url = null;
		try{ 
			url = new URL("file", null, path); 
		} catch(MalformedURLException e) { 
			e.printStackTrace();
		}
		
		dict = new Dictionary(url);
		
		try {
			dict.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		dict.close();
		super.finalize();
	}

	@Override
	public Set<String> getSynonyms(CASSWordSense sense) {
		Set<String> synonyms = new HashSet<String>();
        ISynset synset = getSynset(sense);
    	
    	for (IWord w : synset.getWords()) {
    		synonyms.add(w.getLemma());
    	}
		return synonyms;
	}

	@Override
	public Set<CASSWordSense> getSenses(String word) {
		Set<CASSWordSense> senses = new HashSet<CASSWordSense>();
		
		// getting WordNet indexes for all parts of speech
		List<IIndexWord> indexWords = new ArrayList<IIndexWord>();
		indexWords.add(dict.getIndexWord(word, POS.NOUN));
		indexWords.add(dict.getIndexWord(word, POS.VERB));
		indexWords.add(dict.getIndexWord(word, POS.ADJECTIVE));
		indexWords.add(dict.getIndexWord(word, POS.ADVERB));
		
		Set<IWordID> wordIDList = new HashSet<IWordID>();
		for (IIndexWord indexWord : indexWords) {
			if (indexWord != null) {
				wordIDList.addAll(indexWord.getWordIDs());
			}
		}
				
		for (IWordID wordID : wordIDList) {
			IWord iword = dict.getWord(wordID);
			
			ISenseKey senseKey = iword.getSenseKey();
			
			ISenseEntry senseEntry = dict.getSenseEntry(senseKey);
			
			CASSWordSense sense = new CASSWordSense(iword.getLemma(), senseKey.toString(), iword.getPOS().toString(), senseEntry.getTagCount());
			senses.add(sense);
		}
		
		return senses;
	}

	@Override
	public String getDefinition(CASSWordSense sense) {
        String gloss = null;
        IIndexWord indexWord = getIndexWord(sense);
        
        List<IWordID> wordIDList = new ArrayList<IWordID>();
        if (indexWord != null) {
		    wordIDList.addAll(indexWord.getWordIDs());
		}
        
        IWord w = null;
    	for (IWordID wid : wordIDList) {
    		w = dict.getWord(wid);
    		if (w.getSenseKey().toString().equals(sense.getId())) {
    			gloss = w.getSynset().getGloss();
    			break;
    		}
    	}
    	return gloss;
    	
	}
	
	@Override
	public Set<CASSWordSense> getHypernyms(CASSWordSense sense) {
		Set<CASSWordSense> hypernyms = new HashSet<CASSWordSense>();
		ISynset synset = getSynset(sense);
    	
    	List<ISynsetID> hyperlist = synset.getRelatedSynsets(Pointer.HYPERNYM);
    	List<IWord> wordlist = new ArrayList<IWord>();
    	for (ISynsetID synsetID : hyperlist) {
    		wordlist.addAll(dict.getSynset(synsetID).getWords());
    	}
    	for (IWord w : wordlist) {
    		ISenseKey senseKey = w.getSenseKey();
			ISenseEntry senseEntry = dict.getSenseEntry(senseKey);
    		CASSWordSense s = new CASSWordSense(w.getLemma(), senseKey.toString(), w.getPOS().toString(), senseEntry.getTagCount());
    		hypernyms.add(s);
    	}
    	
		return hypernyms;
	}
	
	/**
	 * Translates a CASSWordSense object into the appropriate ISynset from WordNet
	 * @param sense CASSWordSense object to be translated to ISynset
	 * @return ISynset object for the given CASSWordSense object
	 */
	private ISynset getSynset(CASSWordSense sense) {
		IIndexWord indexWord = getIndexWord(sense);
        
        List<IWordID> wordIDList = new ArrayList<IWordID>();
    	wordIDList.addAll(indexWord.getWordIDs());
    	    	
    	ISynset synset = null;
    	for (IWordID wid : wordIDList) {
    		IWord w = dict.getWord(wid);
    		if (w.getSenseKey().toString().equals(sense.getId())) {
    			synset = w.getSynset();
    			break;
    		}
    	}
    	return synset;
	}
	
	/**
	 * Translates a CASSWordSense object into the appropriate IIndexWord from WordNet
	 * @param sense CASSWordSense object to be translated to IIndexWord
	 * @return IIndexWord object for the given CASSWordSense object
	 */
	private IIndexWord getIndexWord(CASSWordSense sense) {
		IIndexWord indexWord = null;
		
        switch (sense.getPOS()) {
        case "noun":
        	indexWord = dict.getIndexWord(sense.getTarget(), POS.NOUN); // This line should not return null!
        	break;
        case "verb":
        	indexWord = dict.getIndexWord(sense.getTarget(), POS.VERB);
        	break;
        case "adjective":
        	indexWord = dict.getIndexWord(sense.getTarget(), POS.ADJECTIVE);
        	break;
        case "adverb":
        	indexWord = dict.getIndexWord(sense.getTarget(), POS.ADVERB);
        	break;
        default:
        	break;
        }
        
        return indexWord;
	}
	
}
