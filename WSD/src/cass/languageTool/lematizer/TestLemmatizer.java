package cass.languageTool.lematizer;

public class TestLemmatizer implements Lemmatizer {

	@Override
	public String lemmatize(String string) {
		String lemma;
		
		switch (string) {
		case "makes":
			lemma = "make";
			break;
			
		case "musical":
			lemma = "music";
			break;
		
		case "sounds":
			lemma = "sound";

		default:
			lemma = string;
			break;
		}
		
		return lemma;
	}

}
