package controller;

import model.User;
import view.Application;

public class GeneratePdf  implements Validator{
	
	private int i;
	private User user;
	
	
	public GeneratePdf(int i) {
		this.i = i;
		this.user = Application.getInstance().getUsers().getConnectedUser();
	}


	public boolean validate() {
		return user.getObjets().get(i) != null;
	}


	public boolean process() {
		user.getObjets().get(i).createPDF();
		return true;
	}

}
