/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class StringToElement {
	/**
	 * Convert an XML string (with a correct root Element) into an element with his children.
	 * @param XML
	 * @return
	 */
	public static Element getElementFromString(String XML) {
		SAXBuilder saxBuilder=new SAXBuilder();
        Reader stringReader=new StringReader(XML);
        Element root = null;
        try {
			root = saxBuilder.build(stringReader).getRootElement();
		} catch (JDOMException e) {
			System.err.println("Parse problem");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return root;
	}
	
	/**
	 * Convert an XML string without root element into an element with his children.
	 * @param XML
	 * @param rootName The name of the needed root element. Will be added.
	 * @return
	 */
	public static Element getElementFromString(String XML, String rootName) {
		return getElementFromString("<" + rootName + ">" + XML + "</" + rootName + ">");
	}
}
