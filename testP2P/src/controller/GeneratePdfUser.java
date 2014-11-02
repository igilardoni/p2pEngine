package controller;

import model.User;
import view.Application;

public class GeneratePdfUser implements Validator{

	private User user;
	
	
	public GeneratePdfUser() {

		this.user = Application.getInstance().getUsers().getConnectedUser();
	}
	public boolean validate() {
		return user != null;
	}

	@Override
	public boolean process() {
		user.createPDF();
		return true;
	}

}
