package edu.slu.wsd.testGenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Random;

import edu.mit.jsemcor.detokenize.DefaultDetokenizer;
import edu.mit.jsemcor.element.IContext;
import edu.mit.jsemcor.main.IConcordanceSet;
import edu.mit.jsemcor.main.Semcor;

public abstract class TestDataGenerator implements Iterable<TestData>, Iterator<TestData> {

	private IConcordanceSet semcor;
	private Iterator<IContext> contextIter;
	Random rand;
	DefaultDetokenizer detokenizer =  new DefaultDetokenizer();
	
	public TestDataGenerator(long seed) {
		
		this.rand = new Random(seed);
		
		String path = "/usr/lib/cass/textResources/semcor3.0";
		try {
			semcor = new Semcor(new URL("file", null, path));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
