package model;

import net.jxta.endpoint.Message;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;

public class Chatter implements PipeMsgListener{

	
	private UsersManagement users;
	private Peer peer;
	
	public Chatter(UsersManagement users, Peer peer) {
		this.users = users;
		this.peer = peer;
	}
	
	/**
	 * On reçoit un msg: on regarde le l'envoyeur et le destinataire (utilisateur de ce client visé)
	 * et on archive le message
	 */
	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		// TODO Auto-generated method stub
		Message msg = event.getMessage();
		byte[] contentBytes = msg.getMessageElement("Content").getBytes(true);
		byte[] toBytes = msg.getMessageElement("to").getBytes(true);
		byte[] fromBytes = msg.getMessageElement("From").getBytes(true);
		byte[] dateBytes = msg.getMessageElement("Date").getBytes(true);
		
		String content = new String(contentBytes);
		String to = new String(toBytes);
		String from = new String(fromBytes);
		long date = new Long(new String(dateBytes));
		
		User user = users.getUser(to);
		if(user != null) {
			MessageData message = new MessageData(content, date);
			user.getMessages().addMessage(message, from);
		}
		
	}
	
}
