package cass.wsd;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cass.languageTool.Language;
import edu.mit.jsemcor.detokenize.DefaultDetokenizer;
import edu.mit.jsemcor.element.IContext;
import edu.mit.jsemcor.element.ISentence;
import edu.mit.jsemcor.element.IWordform;
import edu.mit.jsemcor.main.IConcordance;
import edu.mit.jsemcor.main.IConcordanceSet;
import edu.mit.jsemcor.main.Semcor;

public class TestLesk {
	
	DefaultDetokenizer detokenizer =  new DefaultDetokenizer();

	@Test
	public void test() {
		WSD wsd = new WSD("The", "bass", "makes low musical sounds", Language.TEST);
		List<ScoredSense> ranked = wsd.rankSensesUsingLesk();
		
		List<String> properID = Arrays.asList("bass0", "bass1");
		List<Integer> properScore = Arrays.asList(3,1);
		
		for (int i = 0; i < ranked.size(); i++) {
			assertEquals(properID.get(i), ranked.get(i).getSense().getId());
			assertEquals(properScore.get(i), (Integer) ranked.get(i).getScore());
			
		}
	}
	
	@Test
	public void test2() {

	    // construct the URL to the Semcor directory
		String path = "semcor3.0";
		URL url = null;
		try {
			url = new URL("file", null, path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// construct the semcor object and open it
		IConcordanceSet semcor = new Semcor(url);
		semcor.open();
		
		IConcordance concord = semcor.get("brown1");
		IContext context = concord.getContext("br-a01");
		
		for (ISentence sentence : context.getSentences()) {
	
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
			
			System.out.println(leftContext);
			System.out.println(senses + "\t" + target);
			System.out.println(rightContext);
			System.out.println();
			
		}
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
