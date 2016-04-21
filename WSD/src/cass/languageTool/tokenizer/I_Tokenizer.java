package cass.languageTool.tokenizer;
import java.util.ArrayList;

public interface I_Tokenizer {
  public ArrayList<String> tokenize(String body);
}
