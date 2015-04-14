package model.pdf;

import java.util.HashMap;

import model.User;


/**
 * Permet de générer un modèle de type utilisateur à partir d'un User
 * @author Ismael Cussac
 * 
 * Champs: modele, filename, pseudoUser, nomUser, prenomUser, adresseUser, telephoneUser, mailUser, noteUser
 */

public class UserPdfModel extends AbstractPdfModel  {

	
	protected HashMap<String,String> userTextMap = new HashMap<String,String>();
	protected HashMap<String,Boolean> userBoolMap = new HashMap<String,Boolean>();
	protected HashMap<String,String> userImageMap = new HashMap<String,String>();
	
	
	private final String modele = "ficheUser";
	private User user;
	
	public UserPdfModel(User user) {
		this.user = user;
		fillMap();
	}
	
	
	private void fillMap() {
		
		addFile();
		addInfos();		
	}


	private void addInfos() {
				
		userTextMap.put("pseudoUser", user.getLogin());
		userTextMap.put("nomUser", user.getNom());
		userTextMap.put("prenomUser", user.getPrenom());
		userTextMap.put("adresseUser", user.getAdresse());
		userTextMap.put("telephoneUser", user.getTel());
		userTextMap.put("mailUser", user.getMail());
		userTextMap.put("noteUser",String.valueOf(user.getMoyenneNotes()));	
	}


	private void addFile() {
		
		userTextMap.put("modele", modele);
		userTextMap.put("fileOut", user.getLogin());	
	}


	public HashMap<String, String> getTexteMap() {
		return userTextMap;
	}

	public HashMap<String, String> getImageMap() {
		return userImageMap;
	}

	public HashMap<String, Boolean> getBoolMap() {
		return userBoolMap;
	}
}
