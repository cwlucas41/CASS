/*
 *   OpenOffice.org extension for syntax highlighting
 *   Copyright (C) 2008  Cédric Bosdonnat cedricbosdo@openoffice.org
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Library General Public
 *   License as published by the Free Software Foundation; 
 *   version 2 of the License.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this library; if not, write to the Free
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.libreoffice.example.comp.tests;

import org.libreoffice.example.comp.CASS2Impl;
import org.libreoffice.example.comp.tests.base.UnoTestCase;



/**
 * This is a sample Unit test case for Uno extensions, feel free to create more of them.
 */
public class ProjectTest extends UnoTestCase {

    public void testSomething() {
    	CASS2Impl s= new CASS2Impl(null);
		s.getSynonym("I", "stab", "at thee", "English", "LeskWithWordNet");
		System.out.println(s.getsynonymCount());
		System.out.println(s.getsynsetCount());

		
		for (int i = 0; i < s.getsynsetCount(); i++) {
			for (int j = 0; j < s.getsynonymCount(); j++) {
				String syn = s.getSyn(i, j);
				if (syn != null) {
					System.out.println(syn);
				}
			}
			System.out.println();
		}
	}
    
    // TODO Add some more tests methods
}
