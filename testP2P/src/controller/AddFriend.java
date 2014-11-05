package controller;

import view.Application;
import model.RemoteRessource;
import model.User;
import model.communications.FriendRequestService;

public class AddFriend implements Validator {

	private String f = null;
	
	public AddFriend(String f) {
		this.f = f;
	}
	
	@Override
	public boolean validate() {
		if(f == null || f.length() == 0) return false;
		RemoteRessource<User> rs = new RemoteRessource<User>(Application.getInstance().getPeer().getDiscovery(), "login", 1000);
		User u = rs.getRemoteRessource(f);
		return u != null;
	}

	@Override
	public boolean process() {
		return FriendRequestService.sendRequest(Application.getInstance().getChatter(), Application.getInstance().getUsers().getConnectedUser().getLogin(), f);
	}

}
