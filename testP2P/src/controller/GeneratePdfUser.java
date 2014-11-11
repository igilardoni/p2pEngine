package controller;

import model.User;
import view.Application;

/**
 * Cree un PDF de type User
 * @author Ismael Cussac
 *
 */
public class GeneratePdfUser implements Validator{

	private User user;
	
	public GeneratePdfUser() {
		this.user = Application.getInstance().getUsers().getConnectedUser();
	}
	
	public boolean validate() {
		return user != null;
	}

	public boolean process() {
		user.createPdf();
		return true;
	}
}
