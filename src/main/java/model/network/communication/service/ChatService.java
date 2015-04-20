package model.network.communication.service;

import net.jxta.endpoint.Message;

/**
 * Chat service for communication. Handle all message for Chat service. For discussion between users.
 * @author Julien Prudhomme
 *
 */
public class ChatService extends Service{

	@Override
	public String getServiceName() {
		return this.getClass().getName();
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
	public boolean handleMessage(Message m) {
		if(!checkMessageFormat(m)) return false;
		String to = new String(m.getMessageElement("to").getBytes(true));
		String fromNick = new String(m.getMessageElement("fromNick").getBytes(true));
		String content = new String(m.getMessageElement("content").getBytes(true));
		String date = new String(m.getMessageElement("date").getBytes(true));
		
		
		//TODO process the message
		
		
		return true;
	}
 // TODO
}
