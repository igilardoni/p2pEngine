package model.network.communication.service.sigma;

import model.network.communication.service.Service;
import model.network.communication.service.sigma.sigmaProtocol.Responses;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

/**
 * Receiver service for sigma's protocols
 * @author Prudhomme Julien
 *
 */
public class Receiver extends ResponseSender{
	
	@Override
	public String getServiceName() {
		return getClass().getName();
	}

	@Override
	public Responses readResponse(String xml) {
		// TODO Auto-generated method stub
		return null;
	}




}
