package cass.LO;

import java.util.List;
import java.util.Set;

import cass.LanguageTool.Language;
import cass.WSD.*;

public class LibreOfficeCass {
	
	private WSD wsd;
	
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
	
	public String[][] getSynonyms(String algorithm) {
		Algorithm enumAlg = null;
		
		switch (algorithm) {
		case "LeskWithWordNet":
			enumAlg = Algorithm.LESK_WITH_WORDNET;
			break;

		default:
			break;
		}
		return convertToArrayOfArrays(wsd.rankSynsetsUsing(enumAlg));
	}
	
	private String[][] convertToArrayOfArrays(List<Set<String>> sortedSynonymSets) {
		return null;
	}
}
