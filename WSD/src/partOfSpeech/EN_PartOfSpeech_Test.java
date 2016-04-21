package partOfSpeech;
import java.util.ArrayList;

public class EN_PartOfSpeech_Test {
	public static void main(String[] args){
		boolean passed = true;
		EN_PartOfSpeech pos = new EN_PartOfSpeech();
		ArrayList<String> test = new ArrayList<String>();
		ArrayList<String> known = new ArrayList<String>();
		
		test.add("What");
		test.add("part");
		test.add("of");
		test.add("speech");
		test.add("is");
		test.add("this");
		
		known.add("WDT");
		known.add("NN");
		known.add("IN");
		known.add("NN");
		known.add("VBZ");
		known.add("DT");
		
		//TODO - Make better test
		for(int i=0; i < test.size(); i++){
			
			if(!pos.getPOStag(test.get(i)).equals(known.get(i))){
				System.out.println("Incorrect POS for word " + test.get(i));
				passed = false;
			}
			
		}
		if(passed){
			System.out.println("All tests passed successfully");
		}
	}

}
