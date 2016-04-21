package cass.wsd;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cass.languageTool.Language;
import partOfSpeech.EN_PartOfSpeech;

public class EN_PartOfSpeech_Test {
	@Test
	public static void test(){
		boolean passed = true;
		EN_PartOfSpeech pos = new EN_PartOfSpeech();
		List<String> test = null;
		List<String> known = null;
		
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
			
			if(!pos.getPOStag(test.get(i)).equals(known.get(i))){
				System.out.println("Incorrect POS for word " + test.get(i));
				passed = false;
			}
			
		}
		if(passed){
			System.out.println("All tests passed successfully");
		}
	}

}
