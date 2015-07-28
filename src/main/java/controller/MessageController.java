package controller;

import model.Application;
import model.data.user.Conversations;
import model.data.user.User;
import model.data.user.UserMessage;
import util.secure.AsymKeysImpl;

/**
 * User message sender controller.
 * @author Prudhomme Julien
 *
 */
public class MessageController {
	
	private UserMessage msg;
	private User u;
	
	public MessageController(String subject, String message, AsymKeysImpl to) {
		u = Application.getInstance().getManager().getUserManager().getCurrentUser();
		if(u == null) {
			throw new RuntimeException("Must be loggued");
		}
		msg = new UserMessage(to, u.getKeys(), subject, message);
	}
	
	public boolean send() {
		
		Application.getInstance().getManager()
				.getMessageManager().getCurrentUserConversations().addMessage(msg);
		
		msg.encrypt();
		msg.sign(u.getKeys());
		Application.getInstance().getManager().getMessageManager().addMessage(msg);
		
		//TODO TRY DIRECT SENDING
		
		return false;
	}
}
