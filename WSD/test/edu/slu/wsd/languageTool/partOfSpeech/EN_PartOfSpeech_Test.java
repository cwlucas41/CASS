package edu.slu.wsd.languageTool.partOfSpeech;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.slu.wsd.languageTool.partOfSpeech.EN_PartOfSpeech;

public class EN_PartOfSpeech_Test {
	@Test
	public void test(){
		
		/*EN_PartOfSpeech pos = new EN_PartOfSpeech();
		List<String> test = new ArrayList<String>();
		List<String> known = new ArrayList<String>();
		
		test.add("What");
		test.add("part");
		test.add("of");
		test.add("speech");
		test.add("is");
		test.add("this");
		
		known.add("WDT");
		known.add("NN");
		known.add("IN");
		known.add("NN");
		known.add("VBZ");
		known.add("DT");
		
		//TODO - Make better test
		for(int i=0; i < test.size(); i++){
			assertEquals(pos.getPOStag(test.get(i)), known.get(i));	
		}*/
		
		EN_PartOfSpeech pos = new EN_PartOfSpeech();
		String left = "What part of speech is the ";
		String tar = "hood";
		String right = " of my car?";
		System.out.println(pos.getPOStag(left,  tar,  right));
		assertEquals(pos.getPOStag(left,  tar,  right), "NN");
	}
}
