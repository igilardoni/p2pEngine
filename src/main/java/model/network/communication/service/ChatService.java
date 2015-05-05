package model.network.communication.service;

import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;



/**
 * Chat service for communication. Handle all message for Chat service. For discussion between users.
 * @author Julien Prudhomme
 *
 */
public class ChatService extends Service<model.network.communication.Message>{

	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}
	
	
	@Override
	/**
	 * A private message is received on this peer. 
	 * You may get this message by adding a listener.
	 */
	public model.network.communication.Message handleMessage(Message m) {
		model.network.communication.Message message = new model.network.communication.Message(new String(m.getMessageElement("content").getBytes(true)));
		
		return message;
	}

	@Override
	public void sendMessage(model.network.communication.Message data,
			PeerID... ids) {
		sender.sendMessage(data.toString(), getServiceName(), ids);
		
	}
}
