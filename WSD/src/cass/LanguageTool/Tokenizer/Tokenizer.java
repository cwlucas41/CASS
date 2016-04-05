package cass.LanguageTool.Tokenizer;
import java.util.List;

public interface Tokenizer {
	List<String> tokenize(String string);
}
