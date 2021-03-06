package edu.slu.cass;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;

public class Cass_Test {

	@Test
	public void targetRemoved() {
		String lc = "Be sure that he took so little hurt from the evil, and escaped in the";
		String target = "End";
		String rc = "because he began his ownership of the Ring so";
		
		Cass s= new Cass(lc, target, rc, "English");
		List<Set<String>> synsets = s.convertToSynonyms(s.getSynonyms("Lesk"));
		for (Set<String> synset : synsets) {
			if (synset.contains(target) || synset.contains(target.toLowerCase())) {
				fail(synset.toString());
			}
		}
	}
	
	@Test
	public void GUI() {
			Cass s= new Cass("Be sure that he took so little hurt from the evil, and escaped in the", "End", "because he began his ownership of the Ring so", "English");
			String result = s.getSynonym("Lesk");
			System.out.println(result);
	}
	
	@Test
	public void Underscoretest(){
		Cass s= new Cass("Be sure that he took so little hurt from the evil, and escaped in the", "end", "because he began his ownership of the Ring so", "English");
		String input,expected,actual;
		input = new String("test_sentence");
		expected = new String("test sentence");
		actual = s.removeUnderscore(input);
		assert(actual.equals(expected));
		input = "";
		expected = "";
		actual = "";
		
		input = new String("_test__sentence_c_a____se");
		expected = new String(" test  sentence c a    se");
		actual = s.removeUnderscore(input);
		assert(actual.equals(expected));
		input = "";
		expected = "";
		actual = "";
		
		input = new String("test-sentence");
		expected = new String("test-sentence");
		actual = s.removeUnderscore(input);
		assert(actual.equals(expected));
		input = "";
		expected = "";
		actual = "";
		
		input = new String("_ _ _ _ _ ");
		expected = new String("          ");
		actual = s.removeUnderscore(input);
		assert(actual.equals(expected));
		input = "";
		expected = "";
		actual = "";
		
	}
	
}
