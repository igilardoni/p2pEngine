package controller;

import view.Application;
import model.RemoteRessource;
import model.User;
import model.communications.FriendRequestService;

/**
 * Ajouter un ami à la liste de contacts
 * @author 
 * @param friend nom de l'ami
 */
public class AddFriend implements Validator {

	private String friend = null;
	
	public AddFriend(String friend) {
		this.friend = friend;
	}
	
	public boolean validate() {
		if(friend == null || friend.length() == 0) return false;
		RemoteRessource<User> rs = new RemoteRessource<User>(Application.getInstance().getPeer().getDiscovery(), "login", 1000);
		User u = rs.getRemoteRessource(friend);
		return u != null;
	}

	public boolean process() {
		return FriendRequestService.sendRequest(Application.getInstance().getChatter(), Application.getInstance().getUsers().getConnectedUser().getLogin(), friend);
	}
}