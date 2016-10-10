package cass.libreOffice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cass.languageTool.Language;
import cass.languageTool.LanguageTool;
import cass.languageTool.wordNet.CASSWordSense;
import cass.wsd.*;

/**
 * Wrapper class for LibreOffice Basic to interact with. Translates calls into WSD calls and translates returns from List to array types.
 * @author cwlucas41
 *
 */
public class LibreOfficeCass {
	
	private WSD wsd;
	
	/**
	 * Constructor for LibreOfficeCass. Converts language String into Language Enumeration type.
	 * @param leftContext - String of words left of the target word in corpus
	 * @param target - target word String
	 * @param rightContext - String of words right of the target word in corpus
	 * @param language - String representing language of test
	 */
	public LibreOfficeCass(String leftContext, String target, String rightContext, String language) {
		Language enumLang = null;
		
		switch (language) {
		case "English":
			enumLang = Language.EN;
			break;

		default:
			break;
		}
		
		wsd = new WSD(leftContext, target, rightContext, enumLang);
	}
	
	/**
	 * Calls WSD algorithm using a string for the algorithm field (translates into Algorithm Enum). 
	 * Then gets synonyms for each sense and translates result from List type to array type
	 * @param algorithm String
	 * @return String array of senses containing synonyms
	 */
	public WSD_Result getSynonyms(String algorithm) {
		List<CASSWordSense> rankedSenses = null;
		
		switch (algorithm) {
		case "LeskWithWordNet":
			rankedSenses = wsd.rankSensesUsing(Algorithm.LESK, 0);
			break;

		default:
			break;
		}
		
		return convert(rankedSenses);
		//return convert(rankedSenses);
	}

	/**
	 * Converts list of senses to nested array of synonyms
	 * @param senses
	 * @return String array for senses containing array for synonyms
	 */
	private WSD_Result convert(List<CASSWordSense> senses) {

		

		List<Set<String>> bufferedConversion = new ArrayList<Set<String>>();
		LanguageTool langTool = wsd.getlTool();
		int rowSize = 0;
		for (CASSWordSense sense : senses) {
			Set<String> synonyms = langTool.getSynonyms(sense);
			bufferedConversion.add(synonyms);
			int thisRowSize = synonyms.size();
			if (thisRowSize > rowSize){
				rowSize = thisRowSize;
			}
		}
		int columnSize = senses.size();
		String[][] nestedArray = new String[columnSize][rowSize];
		int i = 0;
		for (Set<String> synonyms : bufferedConversion) {
			int j = 0;
			for (String synonym : synonyms) {
				nestedArray[i][j] = synonym;
				j++;
			}
			i++;
		}
		
		WSD_Result result = new WSD_Result();
		result.SynsetCount = i;
		result.SynonymCount = rowSize;
		result.Synonyms = nestedArray;
		
		return result;
	}

	
}
