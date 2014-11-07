package controller;

import view.Application;
import model.Objet;
import model.User;

/**
 * Supprimer une annonce de l'utilisateur connecté
 * @param i le rang i de l'annonce chez l'utilisateur courrant.
 */
public class AnnonceDelete implements Validator{
	
	private int i;
	private User user;
	
	public AnnonceDelete(int i) {
		this.i = i;
		this.user = Application.getInstance().getUsers().getConnectedUser();
	}
	
	public boolean validate() {
		return user.getObjets().get(i) != null;
	}

	public boolean process() {
		Objet o = user.getObjets().get(i);
		o.flush(Application.getInstance().getPeer().getDiscovery());
		user.getObjets().delete(i);
		return true;
	}
}