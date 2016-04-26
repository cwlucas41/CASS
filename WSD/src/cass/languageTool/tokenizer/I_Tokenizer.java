package cass.languageTool.tokenizer;
import java.util.List;

/**
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public interface I_Tokenizer {
	/**
	 * @param body A string that represents a body of text to be tokenized.
	 * @return A List of Strings where each element are the tokens of all the sentences in body
	 */
  public List<String> tokenize(String body);
}
