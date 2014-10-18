package controller;

import model.User;
import model.UsersManagement;
import view.Application;

public class SuppCompteConfirm implements Validator{

	private String password;
	private User user = Application.getInstance().getUsers().getConnectedUser();
	
	public boolean errorPassword;
	
	
	public SuppCompteConfirm(String password) {
		this.password = password;
		errorPassword = false;
	}
	
	@Override
	public boolean validate() {
		
		checkPassword();
		return !(errorPassword);
	}

	@Override
	public boolean process() {
		UsersManagement users = Application.getInstance().getUsers();
		users.deleteUser(user.getLogin(), password);
		users.disconnectUser();
		return true;
	}
	
	public void checkPassword() {
		if(!user.isPasswordEqual(password)) errorPassword = true;
	}

}
