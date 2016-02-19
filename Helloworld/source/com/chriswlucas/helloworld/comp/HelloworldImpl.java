package com.chriswlucas.helloworld.comp;

import com.sun.star.uno.XComponentContext;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.lib.uno.helper.WeakBase;

public final class HelloworldImpl extends WeakBase
   implements com.chriswlucas.helloworld.XHelloworld,
              com.sun.star.lang.XServiceInfo
{
    private final XComponentContext m_xContext;
    private static final String m_implementationName = HelloworldImpl.class.getName();
    private static final String[] m_serviceNames = {
        "com.chriswlucas.helloworld.Helloworld" };

    // attributes
    private String m_LadyName = new String();

    public HelloworldImpl( XComponentContext context )
    {
        m_xContext = context;
    };

    public static XSingleComponentFactory __getComponentFactory( String sImplementationName ) {
        XSingleComponentFactory xFactory = null;

        if ( sImplementationName.equals( m_implementationName ) )
            xFactory = Factory.createComponentFactory(HelloworldImpl.class, m_serviceNames);
        return xFactory;
    }

    public static boolean __writeRegistryServiceInfo( XRegistryKey xRegistryKey ) {
        return Factory.writeRegistryServiceInfo(m_implementationName,
                                                m_serviceNames,
                                                xRegistryKey);
    }

    // com.chriswlucas.helloworld.XHelloworld:
    public String getLadyName()
    {
        return m_LadyName;
    }

    public void setLadyName(String the_value)
    {
    	m_LadyName = the_value;
    }

    public String sayHello(boolean isBadBoy)
    {
        String hello = "Hello Mrs. " + getLadyName();
        if (isBadBoy) {
        	hello = "A third is 1/3";
        }
        return hello;
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

}
