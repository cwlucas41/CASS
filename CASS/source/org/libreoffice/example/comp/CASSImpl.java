package org.libreoffice.example.comp;

import com.sun.star.uno.XComponentContext;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

import com.sun.star.lib.uno.helper.Factory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.lib.uno.helper.WeakBase;


public final class CASSImpl extends WeakBase
   implements com.sun.star.lang.XServiceInfo,
              org.libreoffice.example.XCASS
{
    private final XComponentContext m_xContext;
    private static final String m_implementationName = CASSImpl.class.getName();
    private static final String[] m_serviceNames = {
        "org.libreoffice.example.CASS" };
    
    private String wnKey = "wordnet.database.dir";
    private String wnValue = "/home/design/Documents/WordNet-3.0/dict/";

    @Override
    public String getwnKey() {
		return wnKey;
	}

    @Override
	public void setwnKey(String wnKey) {
		this.wnKey = wnKey;
	}

    @Override
	public String getwnValue() {
		return wnValue;
	}

    @Override
	public void setwnValue(String wnValue) {
		this.wnValue = wnValue;
	}

	public CASSImpl( XComponentContext context )
    {
        m_xContext = context;
    };

    public static XSingleComponentFactory __getComponentFactory( String sImplementationName ) {
        XSingleComponentFactory xFactory = null;

        if ( sImplementationName.equals( m_implementationName ) )
            xFactory = Factory.createComponentFactory(CASSImpl.class, m_serviceNames);
        return xFactory;
    }

    public static boolean __writeRegistryServiceInfo( XRegistryKey xRegistryKey ) {
        return Factory.writeRegistryServiceInfo(m_implementationName,
                                                m_serviceNames,
                                                xRegistryKey);
    }

    // com.sun.star.lang.XServiceInfo:
    public String getImplementationName() {
         return m_implementationName;
    }

    public boolean supportsService( String sService ) {
        int len = m_serviceNames.length;

        for( int i=0; i < len; i++) {
            if (sService.equals(m_serviceNames[i]))
                return true;
        }
        return false;
    }

    public String[] getSupportedServiceNames() {
        return m_serviceNames;
    }

    public String[] getSynonym(String target) {
    	// initialize the Synset database
		System.setProperty(getwnKey(), getwnValue());
    	
		// TODO(Fausto): Implement and test against an open knowledge source 
		WordNetDatabase database = WordNetDatabase.getFileInstance();		
		Synset[] mySyns = database.getSynsets(target);
		
		// convert Synset to single dimension List
		ArrayList<String> result = new ArrayList<String>();
		for(int i=0 ; i<mySyns.length;i++){
			String temp[] = mySyns[i].getWordForms();
			for (int j = 0; j < temp.length; j++) {
				result.add(temp[j]);
			}
		}
		// use set to remove duplicated words
		Set<String> uniqeResult = new LinkedHashSet<>(result);
		result.clear();
		result.addAll(uniqeResult);
		
		// remove the target word
		result.remove(target);
		
		// convert list to array
		String[] arrayResult = new String[result.size()];
		arrayResult = result.toArray(arrayResult);
		
		return arrayResult;
	}

}
