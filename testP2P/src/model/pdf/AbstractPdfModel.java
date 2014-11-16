package model.pdf;

import java.util.HashMap;
import java.util.List;

/**
 * Fonctions utilisees pour creer les elements de remplissage des champs du template
 * @author Ismael Cussac
 *
 */
public abstract class AbstractPdfModel {

	/**
	 * Concatene 2 String
	 */
	protected String concat(String string1, String string2){
		return string1+" "+string2;
	}
	
	/**
	 * Concatene une liste de String
	 */
	protected String concat(List<String> strings){
		String ligne = null;
		
		for(String string : strings)
			concat(ligne, string);
		
		return ligne;
	}
	
	/**
	 * Cree un paragraphe a partir d'une liste de String
	 */
	protected String paragraphe(List<String> strings){
		String paragraphe = "";
		
		for(String string : strings)
			paragraphe += "\n"+string;
		
		return paragraphe;
	}
	
	/**
	 * Accesseurs des differentes HashMap
	 */
	public abstract HashMap<String,String> getTexteMap();
	public abstract HashMap<String,String> getImageMap();
	public abstract HashMap<String,Boolean> getBoolMap();
}
