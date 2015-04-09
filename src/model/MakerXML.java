package makerXML;

import java.lang.reflect.Field;

import org.jdom2.Element;

public class MakerXML {
	public static Element toXML(Object c){
		String name = c.getClass().getSimpleName();
		Element racine = new Element(name);
		//System.out.println("<"+name+">");
		Field[] attributes = c.getClass().getDeclaredFields();
		for (int i = 0; i < attributes.length; i++) {
			try {
				boolean access = attributes[i].isAccessible();
				attributes[i].setAccessible(true);
				String balise = attributes[i].getName();
				String contenu = attributes[i].get(c).toString();
				Element temp = new Element(balise);
				temp.addContent(contenu);
				racine.addContent(temp);
				//System.out.println("\t<"+balise+">"+contenu+"</"+balise+">");
				attributes[i].setAccessible(access);
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("</"+name+">");
		return racine;
	}
}