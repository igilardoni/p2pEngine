package model.pdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Objet;

/**
 * Permet de g�n�rer un mod�le de type objet � partir d'un Objet
 * @author Ismael Cussac
 * 
 * Champs du template: modele, filename, coordonneesObjet, objetObjet, dateObjet, resumeObjet,
 *  imageObjet, signatureObjet, venteObjet, trocObjet, propositionObjet, souhaitObjet, titreObjet, prixObjet, contreObjet
 */

public class ObjetPdfModel extends AbstractPdfModel {

	protected HashMap<String,String> objetTextMap = new HashMap<String,String>();
	protected HashMap<String,Boolean> objetBoolMap = new HashMap<String,Boolean>();
	protected HashMap<String,String> objetImageMap = new HashMap<String,String>();
	
	
	private final String modele = "ficheObjet";
	private Objet objet;

	
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
	
	private void addFile() {
		
		objetTextMap.put("modele", modele);
		objetTextMap.put("fileOut", objet.getTitre());
	}
	
	private void addFicheObjet() {
		
		objetTextMap.put("titreObjet", objet.getTitre());
		objetTextMap.put("resumeObjet", objet.getResume());
		objetImageMap.put("imageObjet", objet.getImg());
		
		objetBoolMap.put("venteObjet", objet.isVente());
		objetBoolMap.put("trocObjet", objet.isTroc());
		objetBoolMap.put("souhaitObjet", objet.isSouhait());
		objetBoolMap.put("propositionObjet", objet.isProposition());
	}


	private void addSignature() {
		objetTextMap.put("signatureObjet", objet.getUser().getLogin());
	}


	private void addDate() {
		objetTextMap.put("dateObjet", objet.getSimpleDate());
	}


	private void addObjet() {
		objetTextMap.put("objetObjet", "Objet: Fiche d'objet");
	}


	private void addCoordonnees() {
		
		String nom = concat(objet.getUser().getPrenom(), objet.getUser().getNom());
		List<String> liste = new ArrayList<String>();
		
		liste.add(nom);
		liste.add(objet.getUser().getAdresse());
		liste.add(objet.getUser().getMail());
		liste.add(objet.getUser().getTel());
		
		String coordonnees = paragraphe(liste);
		
		objetTextMap.put("coordonneesObjet", coordonnees);
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