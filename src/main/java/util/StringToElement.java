package util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.TextElement;
import net.jxta.document.XMLElement;
import net.jxta.impl.document.DOMXMLElement;

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
