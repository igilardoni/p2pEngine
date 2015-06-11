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
		// TODO Auto-generated method stub
		
	}


	@Override
	public UpdateMessage handleMessage(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

}
