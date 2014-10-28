package model;

import java.util.HashMap;
import java.util.List;

public abstract class AbstractPdfModel {

	protected HashMap<String,String> objetTextMap = new HashMap<String,String>();
	protected HashMap<String,Boolean> objetBoolMap = new HashMap<String,Boolean>();
	protected HashMap<String,String> objetImageMap = new HashMap<String,String>();
	
	protected Objet objet;
	
	protected String concat(String string1, String string2){
		return string1+" "+string2;
	}
	
	protected String concat(List<String> strings){
		String ligne = null;
		
		for(String string : strings)
			concat(ligne, string);
		
		return ligne;
	}
	
	protected String paragraphe(List<String> strings){
		String paragraphe = "";
		
		for(String string : strings)
			paragraphe += "\n"+string;
		
		return paragraphe;
	}
	
}
