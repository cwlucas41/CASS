package cass.languageTool.tokenizer;
import java.util.List;

public interface I_Tokenizer {
  public List<String> tokenize(String body);
}
