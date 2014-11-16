package controller;

import view.Application;
import model.User;
import model.UsersManagement;
import model.communications.ChatService;
import model.communications.MessageData;

/**
 * Publier un message dans le chat
 * @author Prudhomme Julien
 *
 */
public class Messages implements Validator {

	private String message = null;
	private String to = null;
	private UsersManagement users;
	
	/**
	 * Pour envoyer un message à l'utilisateur to
	 * @param message le message à envoyer
	 * @param to le destinataire.
	 */
	public Messages(String message, String to) {
		users = Application.getInstance().getUsers();
		this.message = message;
		this.to = to;
	}
	
	@Override
	public boolean validate() {
		if(message == null) return false;
		if(to == null) return false;
		if(message.length() == 0) return false;
		return true;
	}

	@Override
	public boolean process() {
		User user = users.getConnectedUser();
		
		user.getMessages().addMessage(new MessageData(user.getLogin(), to, message, System.currentTimeMillis()), to);
		return ChatService.sendMessage(Application.getInstance().getChatter(), user.getLogin(), to, message);
	}
}