package model.network.communication.service.update;

import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;
import model.network.communication.service.Service;

public class UpdateService extends Service<UpdateMessage>{

	

	
	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}


	@Override
	public void sendMessage(UpdateMessage data, PeerID... ids) {
		switch(data.getType()) {
		case "User": break;
		case "Item": break;
		default:     break;
		}
		
		sender.sendMessage(data.toString(), this.getClass().getSimpleName(), null);
		
	}

	
	private UpdateMessage handleUser(UpdateMessage m) {
		
		
		return m;
	}
	
	private UpdateMessage handleItem(UpdateMessage m) {
		
		
		
		return m;
	}

	@Override
	public UpdateMessage handleMessage(Message m) {
		String content = new String(m.getMessageElement("content").getBytes(true));
		UpdateMessage update = new UpdateMessage(content);
		switch(update.getType()) {
		case "User":   return handleUser(update);
		case "Item": return handleItem(update);
		default: return null;
		}
	}

}