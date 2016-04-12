package cass.languageTool.tokenizer;
import java.util.List;

public interface Tokenizer {
	List<String> tokenize(String string);
}
