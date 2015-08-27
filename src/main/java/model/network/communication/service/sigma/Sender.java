package model.network.communication.service.sigma;

import model.network.communication.service.Service;
import model.network.communication.service.sigma.sigmaProtocol.Responses;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;


/**
 * Sender service for Sigma's protocols
 * @author Prudhomme Julien
 *
 */
public class Sender extends ResponseSender{

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
