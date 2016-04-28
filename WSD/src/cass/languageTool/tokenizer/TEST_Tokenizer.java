package cass.languageTool.tokenizer;

import java.util.Arrays;
import java.util.List;

/**
 * Simple tokenizer used for limited testing
 * @author cwlucas41
 *
 */
public class TEST_Tokenizer implements I_Tokenizer {

	@Override
	public List<String> tokenize(String string) {
		
		switch (string) {
		case "The":
			return Arrays.asList("the");

		case "makes low musical sounds":
			return Arrays.asList("makes", "low", "musical", "sounds");

		case "the lowest part of the musical range":
			return Arrays.asList("the", "low", "part", "of", "the", "musical", "range");
			
		case "the lean flesh of a saltwater fish of the family Serranidae":
			return Arrays.asList("the", "lean", "flesh", "of", "a", "saltwater", "fish", "of", "the", "family", "serranidae");
			
		default:
			return null;
		}
		
	}

}
