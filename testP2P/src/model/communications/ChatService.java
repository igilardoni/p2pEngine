package model.communications;

import model.User;
import model.UsersManagement;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;

/**
 * Service de discussion
 * @author Prudhomme Julien
 *
 */

public class ChatService extends Service {

	private UsersManagement users;
	
	/**
	 * Cree le service de discussion. Doit être ajouté a Chatter.
	 * @param users
	 */
	public ChatService(UsersManagement users) {
		this.users = users;
	}
	
	@Override
	public String getServiceName() {
		return getServName();
	}
	
	public static String getServName() {
		return ChatService.class.getName();
	}

	@Override
	public MessageData handleMessage(Message message) {
		byte[] contentBytes = message.getMessageElement("Content").getBytes(true);
		byte[] toBytes = message.getMessageElement("To").getBytes(true);
		byte[] fromBytes = message.getMessageElement("From").getBytes(true);
		byte[] dateBytes = message.getMessageElement("Date").getBytes(true);
		
		String content = new String(contentBytes);
		String to = new String(toBytes);
		String from = new String(fromBytes);
		long date = new Long(new String(dateBytes));
		
		User user = users.getUser(to);
		if(user != null) { //si le destinataire existe bien sur ce client, on lui donne le message
			MessageData messageData = new MessageData(from, to, content, date);
			user.getMessages().addMessage(messageData, from);
			return messageData;
		}
		return null;
		
	}
	
	/**
	 * Envoit un message à l'utilisateur to
	 * @param chatter l'outil de communication
	 * @param from l'utilisateur de ce client qui envoit le message
	 * @param to l'username du destinataire
	 * @param content le contenu du message
	 * @return
	 */
	public static boolean sendMessage(Chatter chatter, String from, String to, String content) {
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement("From", null, from.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("To", null, to.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Content", null, content.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Date", null, Long.toString(System.currentTimeMillis()).getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement(Chatter.toServiceTag, null, getServName().getBytes(), null));
		return chatter.sendToUser(to, message);
	}

}
