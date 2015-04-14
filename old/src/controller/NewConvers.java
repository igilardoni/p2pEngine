package controller;

import view.Application;
import model.RemoteRessource;
import model.User;

/**
 * Creer une nouvelle conversation dans le chat
 * @author 
 *
 */
public class NewConvers implements Validator {

	private String userName;
	
	public NewConvers(String userName) {
		this.userName = userName;
	}
	
	public boolean validate() {
		
		RemoteRessource<User> rs = new RemoteRessource<User>(Application.getInstance().getPeer().getDiscovery(), "login", 2500);
		return rs.getRemoteRessource(userName) != null;
	}

	public boolean process() {
		User u = Application.getInstance().getUsers().getConnectedUser();
		u.getMessages().newConversation(userName);
		return true;
	}
}