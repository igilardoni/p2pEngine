package p2pEngine;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Gère les utilisateurs
 * @author Prudhomme Julien
 * @author Cussac Ismael
 *
 */
public class UsersManagement implements UsersManagementInterface{

	private HashMap<String, Utilisateur> users = new HashMap<String, Utilisateur>();
	
	@Override
	public String[] getUsersNames() {
		return (String[]) users.keySet().toArray();
	}

	@Override
	public boolean addUser(Utilisateur newUser) {
		if(users.containsKey(newUser.getNom())) return false;
		users.put(newUser.getNom(), newUser);
		return true;
	}

	@Override
	public boolean deleteUser(String nom, String password) {
		if(!checkLogin(nom, password)) return false;
		users.remove(nom);
		return true;
	}

	@Override
	public boolean checkLogin(String nom, String password) {
		if(!users.containsKey(nom)) return false;
		if(!users.get(nom).getPassword().equals(password)) return false;
		return true;
	}

	@Override
	public Utilisateur getUser(String nom, String password) {
		if(!checkLogin(nom, password)) return null;
		return users.get(nom);
	}

}
