package controller;

import view.Application;
import model.User;
import model.search.RemoteRessource;

/**
 * Creer une nouvelle conversation dans le chat
 * @author Prudhomme Julien
 *
 */
public class NewConvers implements Validator {

	private String userName;
	
	/**
	 * Creer une nouvelle conversation
	 * @param userName le destinataire.
	 */
	public NewConvers(String userName) {
		this.userName = userName;
	}
	
	@Override
	public boolean validate() {
		
		RemoteRessource<User> rs = new RemoteRessource<User>(Application.getInstance().getPeer().getDiscovery(), "login", 2500);
		return rs.getRemoteRessource(userName) != null;
	}

	@Override
	public boolean process() {
		User u = Application.getInstance().getUsers().getConnectedUser();
		u.getMessages().newConversation(userName);
		return true;
	}
}