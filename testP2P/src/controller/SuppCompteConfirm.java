package controller;

import model.User;
import model.UsersManagement;
import view.Application;

/** 
 * Controller pour la suppression du compte connecté.
 * @author Prudhomme Julien
 *
 */
public class SuppCompteConfirm implements Validator{

	private String password;
	private User user = Application.getInstance().getUsers().getConnectedUser();
	public boolean errorPassword;
	
	/**
	 * Supprime le compte connecté
	 * @param password Le mot de passe de l'utilisateur connecté
	 */
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
		user.flush(Application.getInstance().getPeer().getDiscovery());
		return true;
	}
	
	/**
	 * Les mots de passes doivent être égaux
	 */
	public void checkPassword() {
		if(!user.isPasswordEqual(password)) errorPassword = true;
	}
}