package model.network.communication.service.sigma;

import model.network.communication.service.Service;
import model.network.communication.service.sigma.sigmaProtocol.Responses;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

/**
 * Trusted party service for Sigma's protocols
 * @author Prudhomme Julien
 *
 */
public class Trent extends Service<Responses>{

	@Override
	public String getServiceName() {
		return getClass().getName();
	}

	@Override
	public Responses handleMessage(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(Responses data, PeerID... ids) {
		// TODO Auto-generated method stub
		
	}
}
