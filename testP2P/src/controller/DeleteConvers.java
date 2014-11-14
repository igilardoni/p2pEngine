package controller;

import view.Application;
import model.User;


/**
 * Controlleur pour supprimer une conversation de la messagerie
 * @author Prudhomme Julien
 *
 */
public class DeleteConvers implements Validator{
	private String userName;
	private User user;
	
	/**
	 * 
	 * @param userName La nom de la conversation a supprimer dont le destinataire est userName
	 */
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
