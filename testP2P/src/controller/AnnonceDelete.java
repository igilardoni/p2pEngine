package controller;

import view.Application;
import model.Objet;
import model.User;


/**
 * Controlleur pour la suppression d'annonce.
 * @author Prudhomme Julien
 *
 */
public class AnnonceDelete implements Validator{
	
	private int i;
	private User user;
	
	
	/**
	 * Supprimer une annonce de l'utilisateur connecte
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
		Objet o = user.getObjets().get(i);
		o.flush(Application.getInstance().getPeer().getDiscovery());
		user.getObjets().delete(i);
		return true;
	}
}