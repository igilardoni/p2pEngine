package controller;

import view.Application;
import model.RemoteRessource;
import model.User;
import model.communications.FriendRequestService;

/**
 * Controlleur pour l'ajout d'ami
 * @author Prudhomme Julien
 */
public class AddFriend implements Validator {

	private String friend = null;
	
	
	/**
	 * Ajoute un ami
	 * @param friend un utilisateur
	 */
	public AddFriend(String friend) {
		this.friend = friend;
	}
	
	@Override
	public boolean validate() {
		if(friend == null || friend.length() == 0) return false;
		RemoteRessource<User> rs = new RemoteRessource<User>(Application.getInstance().getPeer().getDiscovery(), "login", 1000);
		User u = rs.getRemoteRessource(friend);
		return u != null;
	}

	@Override
	public boolean process() {
		return FriendRequestService.sendRequest(Application.getInstance().getChatter(), Application.getInstance().getUsers().getConnectedUser().getLogin(), friend);
	}
}