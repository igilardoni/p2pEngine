package controller;

import view.Application;
import model.User;

public class DeleteConvers implements Validator{
	private String userName;
	private User user;
	
	public DeleteConvers(String userName) {
		this.userName = userName;
		user = Application.getInstance().getUsers().getConnectedUser();
	}
	
	@Override
	public boolean validate() {
		return user.getMessages().getMessages(userName) != null;
	}

	@Override
	public boolean process() {
		user.getMessages().deleteConvest(userName);
		return true;
	}

}
