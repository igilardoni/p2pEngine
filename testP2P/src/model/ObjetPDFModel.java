package model;

import java.util.HashMap;

public class ObjetPDFModel {

	private HashMap<String,String> objetMap = new HashMap<String,String>();
	Objet objet;
	
	public ObjetPDFModel(Objet objet) {
		this.objet = objet;
		fillMap();
	}
	
	
	private void fillMap() {
		
		
	}


	public HashMap<String,String> getHashMap(){
		return objetMap;
	}
	

}
