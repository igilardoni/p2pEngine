package controller;

import model.User;
import model.UsersManagement;
import view.Application;

/** 
 * Supprimer un compte utilisateur
 * @author
 *
 */
public class SuppCompteConfirm implements Validator{

	private String password;
	private User user = Application.getInstance().getUsers().getConnectedUser();
	public boolean errorPassword;
	
	public SuppCompteConfirm(String password) {
		this.password = password;
		errorPassword = false;
	}
	
	public boolean validate() {
		checkPassword();
		return !(errorPassword);
	}

	public boolean process() {
		UsersManagement users = Application.getInstance().getUsers();
		users.deleteUser(user.getLogin(), password);
		users.disconnectUser();
		user.flush(Application.getInstance().getPeer().getDiscovery());
		return true;
	}
	
	public void checkPassword() {
		if(!user.isPasswordEqual(password)) errorPassword = true;
	}
}