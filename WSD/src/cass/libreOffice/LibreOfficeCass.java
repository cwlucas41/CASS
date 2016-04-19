package cass.libreOffice;

import java.util.List;

import cass.languageTool.Language;
import cass.languageTool.wordNet.WordSense;
import cass.wsd.*;

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
		List<WordSense> rankedSenses = null;
		
		switch (algorithm) {
		case "LeskWithWordNet":
			rankedSenses = wsd.rankSynsetsUsing(Algorithm.LESK);
			break;

		default:
			break;
		}
		
		return convert(rankedSenses);
	}

	private String[][] convert(List<WordSense> senses) {
		return null;
	}
	
}
