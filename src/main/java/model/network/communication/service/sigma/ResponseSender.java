package model.network.communication.service.sigma;

import model.network.communication.service.Service;
import model.network.communication.service.sigma.sigmaProtocol.Responses;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

public abstract class ResponseSender extends Service<Responses>{


	@Override
	public Responses handleMessage(Message m) {
		return readResponse(m.getMessageElement("content").getBytes(true).toString());
	}
	
	public abstract Responses readResponse(String xml);

	@Override
	public void sendMessage(Responses data, PeerID... ids) {
		sender.sendMessage(data.toString(), getServiceName(), ids);
	}

}
