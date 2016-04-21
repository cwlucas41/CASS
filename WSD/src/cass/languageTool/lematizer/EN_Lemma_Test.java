package cass.languageTool.lematizer;
public class EN_Lemma_Test {
	public static void main(String[] args){
		boolean passed = true;
		
		EN_Lemma lem = new EN_Lemma();
		
		String test = new String();
		
		test = lem.Lemmatize("had");
		if(!test.equals("have")){
			System.out.println("Error with word " + test);
			passed = false;
		}
		
		test = lem.Lemmatize("eating");
		if(!test.equals("eat")){
			System.out.println("Error with word " + test);
			passed = false;
		}
		
		test = lem.Lemmatize("car");
		if(!test.equals("car")){
			System.out.println("Error with word " + test);
			passed = false;
		}
		
		test = lem.Lemmatize("Fausto");
		if(!test.equals("Fausto")){
			System.out.println("Error with word " + test);
			passed = false;
		}
		
		if(passed){
			System.out.println("All tests passed successfully");
			
		}
		
	}

}
