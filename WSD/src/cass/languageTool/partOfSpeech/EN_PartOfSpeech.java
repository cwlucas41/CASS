package cass.languageTool.partOfSpeech;
import edu.stanford.nlp.simple.Sentence;

public class EN_PartOfSpeech implements I_PartOfSpeech {

	@Override
	public String getPOStag(String word) {
		String result = new String();
		Sentence sen = new Sentence(word);
		result = sen.posTag(0);		
		return result;
	}

}
