package controller;

import view.Application;
import model.UsersManagement;

public class Connexion implements Validator{

	private String login;
	private String password;
	private UsersManagement users = Application.getInstance().getUsers();
	
	public boolean errorLogin;
	public boolean errorPassword;
	
	public Connexion(String login, String password) {
		this.login = login;
		this.password = password;
		
		errorLogin = errorPassword = false;
	}
	
	@Override
	public boolean validate() {
		if(!users.checkLogin(login, password)) {
			errorLogin = errorPassword = true;
		}
		return !(errorLogin || errorPassword);
	}

	@Override
	public boolean process() {
		return users.connectUser(login, password);
	}

}
