package controller;

import view.Application;
import model.Objet;
import model.User;

public class AnnonceDelete implements Validator{
	
	private int i;
	private User user;
	
	
	/**
	 * Controller de l'affichage des annonces utilisateur
	 * @param i le rang i de l'annonce chez l'utilisateur courrant.
	 */
	public AnnonceDelete(int i) {
		this.i = i;
		this.user = Application.getInstance().getUsers().getConnectedUser();
	}
	
	@Override
	public boolean validate() {
		return user.getObjets().get(i) != null;
	}

	@Override
	public boolean process() {
		user.getObjets().delete(i);
		return true;
	}

}
