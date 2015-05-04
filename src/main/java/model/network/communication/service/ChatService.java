package model.network.communication.service;

import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

/**
 * Chat service for communication. Handle all message for Chat service. For discussion between users.
 * @author Julien Prudhomme
 *
 */
public class ChatService extends Service<Message>{

	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	
	/**
	 * Check if the message format is correct
	 * ChatService actually require in addition theses elements :
	 * <to>The aimed public key</to>
	 * <fromnick>The author nickname</fromnick>
	 * <content>The message !</content>
	 * <date>message's date of emit</date>
	 * @return true if we got all excepted elements.
	 */
	private boolean checkMessageFormat(Message m) {
		return 
				m.getMessageElement("to") != null &
				m.getMessageElement("fromnick") != null &
				m.getMessageElement("content") != null &
				m.getMessageElement("date") != null;
	}
	
	@Override
	public Message handleMessage(Message m) {
		if(!checkMessageFormat(m)) return null;
		String to = new String(m.getMessageElement("to").getBytes(true));
		String fromNick = new String(m.getMessageElement("fromNick").getBytes(true));
		String content = new String(m.getMessageElement("content").getBytes(true));
		String date = new String(m.getMessageElement("date").getBytes(true));
		
		
		//TODO process the message
		
		
		return m;
	}
 // TODO


	@Override
	public void sendMessage(Message m, PeerID ...ids) {
		// TODO Auto-generated method stub
		
	}
}
