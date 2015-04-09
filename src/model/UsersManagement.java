package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import net.jxta.discovery.DiscoveryService;


/**
 * Gère les utilisateurs
 * @author Prudhomme Julien
 * @author Cussac Ismael
 *
 */
public class UsersManagement implements UsersManagementInterface, Serializable{

	private static final long serialVersionUID = 2194263777593803642L;
	private HashMap<String, User> users = new HashMap<String, User>();
	private User connectedUser = null;
	
	@Override
	public String[] getUsersNames() {
		if(users.size() == 0) return new String[]{""};
		
		String[] res = new String[users.size()];
		int i = 0;
		for(String s: users.keySet()) {
			res[i] = s;
			i++;
		}
		
		return res;
	}

	@Override
	public boolean addUser(User newUser) {
		if(users.containsKey(newUser.getLogin())) return false;
		users.put(newUser.getLogin(), newUser);
		return true;
	}

	@Override
	public boolean deleteUser(String login, String password) {
		if(!checkLogin(login, password)) return false;
		users.remove(login);
		return true;
	}

	@Override
	public boolean checkLogin(String login, String password) {
		if(!users.containsKey(login)) return false;
		if(!users.get(login).checkPassword(password)) return false;
		return true;
	}

	@Override
	public User getUser(String login, String password) {
		if(!checkLogin(login, password)) return null;
		return users.get(login);
	}
	
	@Override
	public boolean connectUser(String login, String password) {
		this.connectedUser = getUser(login, password);
		return connectedUser != null;
	}
	
	@Override
	public boolean disconnectUser(){
		User usr = connectedUser;
		connectedUser = null;
		return usr != null;
	}

	@Override
	public boolean userExists(String login) {
		return users.containsKey(login);
	}

	@Override
	public User getConnectedUser() {
		return this.connectedUser;
	}
	
	public void publishUsers(DiscoveryService discovery) {
		for(User u: users.values()) {
			u.publish(discovery);
			u.publishObjects(discovery);
		}
	}
	
	/**
	 * Permet d'obtenir un user juste avec son login
	 * Ne devrais pas être appelé en dehors du model
	 * @param login
	 * @return
	 */
	public User getUser(String login) {
		return this.users.get(login);
	}

}
