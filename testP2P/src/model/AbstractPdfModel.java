package model;

import java.util.List;

public abstract class AbstractPdfModel {

	
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
