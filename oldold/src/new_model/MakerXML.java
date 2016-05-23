package new_model;

import java.lang.reflect.Field;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class MakerXML {
	public static Element toXML(Object c, String ...attributs){
		String[] list_ignor = attributs;
		String name = c.getClass().getSimpleName();
		Element racine = new Element("");
		//System.out.println("<"+name+">");
		Field[] attributes = c.getClass().getDeclaredFields();
		for (int i = 0; i < attributes.length; i++) {
			try {attributes[i].setAccessible(true);
				String balise = attributes[i].getName();
				if(!verif(balise,list_ignor)){
					String contenu = attributes[i].get(c).toString();
					Element temp = new Element(balise);
					temp.addContent(contenu);
					racine.addContent(temp);
					//System.out.println("\t<"+balise+">"+contenu+"</"+balise+">");
					attributes[i].setAccessible(false);
					}
				
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
			
		}
		//System.out.println("<"+name+">");
		return racine;
	}
	
	public static boolean verif(String balise, String[] list){
		for (int i = 0; i < list.length; i++) {
			if(balise.equals(list[i]))
				return false;
		}
		
		return true;
	}
	
	public static void affichage(Element racin){
		 try
		   {
		      //On utilise ici un affichage classique avec getPrettyFormat()
		      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		      sortie.output(racin, System.out);
		   }
		   catch (java.io.IOException e){}
	}
}