package cass.LanguageTool.Thesaurus;

import java.util.Set;

public interface Thesaurus {
	
	Set<String> getSynonyms(String word);
	
	// TODO: identify full Thesaurus interface requirements
}
