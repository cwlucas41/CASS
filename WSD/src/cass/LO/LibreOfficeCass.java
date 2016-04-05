package cass.LO;

import java.util.List;
import java.util.Set;

import cass.LanguageTool.Language;
import cass.WSD.*;

public class LibreOfficeCass {
	
	private WSD wsd;
	
	public LibreOfficeCass(String leftContext, String target, String rightContext, Language language) {
		wsd = new WSD(leftContext, target, rightContext, language);
	}
	
	public String[][] getSynonyms(Algorithm algorithm) {
		return convertToArrayOfArrays(wsd.rankSynsetsUsing(algorithm));
	}
	
	private String[][] convertToArrayOfArrays(List<Set<String>> sortedSynonymSets) {
		return null;
	}
}
