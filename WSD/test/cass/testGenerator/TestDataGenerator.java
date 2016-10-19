package cass.testGenerator;

import java.net.URL;
import java.util.Iterator;

import edu.mit.jsemcor.element.IContext;
import edu.mit.jsemcor.main.IConcordanceSet;
import edu.mit.jsemcor.main.Semcor;

public abstract class TestDataGenerator implements Iterable<TestData>, Iterator<TestData> {

	private IConcordanceSet semcor;
	private Iterator<IContext> contextIter;
	
	public TestDataGenerator() {
		
		URL url = getClass().getClassLoader().getResource("semcor3.0");
//		try {
//			semcor = new Semcor(new URL("file", null, path));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		semcor = new Semcor(url);
		semcor.open();
		
		contextIter = semcor.iterator();
	}
	
	public Iterator<IContext> getContextIter() {
		return contextIter;
	}

	@Override
	protected void finalize() throws Throwable {
		semcor.close();
		super.finalize();
	}
	
}
