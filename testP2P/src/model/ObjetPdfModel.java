package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//modele, filename, coordonnees, objet, date, resume, image, signature, vente, troc, proposition, souhait, titre

public class ObjetPdfModel extends AbstractPdfModel {

	
	private final String modele = "ficheObjet";

	
	public ObjetPdfModel(Objet objet) {
		this.objet = objet;
		fillMap();
	}
	
	
	private void fillMap() {
		
		addFile();
		addCoordonnees();
		addObjet();
		addDate();
		addFicheObjet();
		addSignature();
				
	}
	
	private void addFicheObjet() {
		objetTextMap.put("titre", objet.getTitre());
		objetTextMap.put("resume", objet.getResume());
		objetImageMap.put("image", objet.getImg());
		
		objetBoolMap.put("vente", objet.isVente());
		objetBoolMap.put("troc", objet.isTroc());
		objetBoolMap.put("souhait", objet.isSouhait());
		objetBoolMap.put("proposition", objet.isProposition());
		
	}


	private void addSignature() {
		objetTextMap.put("signature", concat(objet.getUser().getPrenom(), objet.getUser().getNom()));
		
	}


	private void addDate() {
		objetTextMap.put("date", objet.getSimpleDate());
		
	}


	private void addObjet() {
		objetTextMap.put("objet", "Objet: Fiche d'objet");
		
	}


	private void addCoordonnees() {
		String nom = concat(objet.getUser().getPrenom(), objet.getUser().getNom());
		List<String> liste = new ArrayList<String>();
		
		liste.add(nom);
		liste.add(objet.getUser().getAdresse());
		liste.add(objet.getUser().getMail());
		liste.add(objet.getUser().getTel());
		
		String coordonnees = paragraphe(liste);
		
		objetTextMap.put("coordonnees", coordonnees);
	}


	private void addFile() {
		objetTextMap.put("modele", modele);
		objetTextMap.put("filename", objet.getTitre());
		
	}


	public HashMap<String,String> getTexteMap(){
		return objetTextMap;
	}
	
	public HashMap<String,String> getImageMap(){
		return objetImageMap;
	}
	
	public HashMap<String,Boolean> getBoolMap(){
		return objetBoolMap;
	}
}

