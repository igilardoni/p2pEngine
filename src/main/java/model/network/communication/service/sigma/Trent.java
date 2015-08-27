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
public class Trent extends ResponseSender{

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
