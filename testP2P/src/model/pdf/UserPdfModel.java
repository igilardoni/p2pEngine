package model.pdf;

import java.util.HashMap;

import model.User;

//Champs: pseudo, nom, prenom, adresse, telephone, mail, note

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
				
		userTextMap.put("pseudo", user.getLogin());
		userTextMap.put("nom", user.getNom());
		userTextMap.put("prenom", user.getPrenom());
		userTextMap.put("adresse", user.getAdresse());
		userTextMap.put("telephone", user.getTel());
		userTextMap.put("mail", user.getMail());
		userTextMap.put("note",String.valueOf(user.getMoyenneNotes()));
		
	}


	private void addFile() {
		userTextMap.put("modele", modele);
		userTextMap.put("filename", user.getLogin());
		
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
