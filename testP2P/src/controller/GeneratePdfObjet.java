package controller;

import model.User;
import view.Application;

/**
 * Cree un PDF de type Objet
 * @author Ismael Cussac
 * @param i numero du l'Objet
 */
public class GeneratePdfObjet implements Validator{
	
	private int i;
	private User user;
	
	/**
	 * Génére le PDF d'un objet
	 * @param i la position de l'objet dans la liste de l'utilisateur
	 */
	public GeneratePdfObjet(int i) {
		this.i = i;
		this.user = Application.getInstance().getUsers().getConnectedUser();
	}

	@Override
	public boolean validate() {
		return user.getObjets().get(i) != null;
	}

	@Override
	public boolean process() {
		user.getObjets().get(i).createPdf();
		return true;
	}
}
