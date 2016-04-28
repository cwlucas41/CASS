package cass.languageTool.tokenizer;
import java.util.List;

/**
 * Interface for all tokenizers
 * @author Fausto Tommasi
 * @version 1.0 4/25/2016
 */
public interface I_Tokenizer {
	
	/**
	 * splits a String into a list of token Strings
	 * @param body A string that represents a body of text to be tokenized.
	 * @return A List of Strings where each element are the tokens of all the sentences in body
	 */
  public List<String> tokenize(String body);
}
