package controller.controllerInterface;

import java.util.ArrayList;

import model.data.user.UserMessage;

/**
 * 
 * @author michael
 * @deprecated
 */
public interface MessageSenderInterface {
	public boolean sendMessageToNick(String nick, String message);
	public boolean sendMessageToPublicKey(String publicKey, String message);
	
	public ArrayList<UserMessage> getMessagesfrom(String publicKey);
}
