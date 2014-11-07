package controller;

import model.User;
import view.Application;

/**
 * Crée un PDF de type Objet
 * @author Ismael Cussac
 * @param i numero du l'Objet
 */
public class GeneratePdfObjet implements Validator{
	
	private int i;
	private User user;
	
	public GeneratePdfObjet(int i) {
		this.i = i;
		this.user = Application.getInstance().getUsers().getConnectedUser();
	}

	public boolean validate() {
		return user.getObjets().get(i) != null;
	}

	public boolean process() {
		user.getObjets().get(i).createPdf();
		return true;
	}
}
