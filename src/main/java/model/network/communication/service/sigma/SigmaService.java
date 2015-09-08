package model.network.communication.service.sigma;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import model.Application;
import model.data.contrat.Contrat;
import model.data.manager.UserManager;
import model.data.user.User;
import model.network.communication.service.Service;
import model.network.communication.service.sigma.sigmaProtocol.Masks;
import model.network.communication.service.sigma.sigmaProtocol.Receiver;
import model.network.communication.service.sigma.sigmaProtocol.ResEncrypt;
import model.network.communication.service.sigma.sigmaProtocol.ResponsesCCE;
import model.network.communication.service.sigma.sigmaProtocol.ResponsesSchnorr;
import model.network.communication.service.sigma.sigmaProtocol.Sender;
import model.network.communication.service.sigma.sigmaProtocol.Trent;
import model.network.communication.service.sigma.sigmaProtocol.Utils;
import model.network.search.RandomPeerFinder;
import model.network.search.Search;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

/**
 * Communication class for sigma protocols. Contain Receivers and Senders(provers) service for each Users
 * and the Trent service
 * @author Prudhomme Julien
 *
 */
public class SigmaService extends Service{
	private Application app;
	private UserManager um;
	
	public SigmaService(Application app) {
		this.app = app;
		um = app.getManager().getUserManager();
	}
	
	private User chooseTrent(String receiverKey) {
		
		Search<User> s = new Search<User>(app.getNetwork(), User.class.getSimpleName(), "superPublicKey", true);
		s.search(null, 3000, 5);
		
		for(User u: s.getResults()) {
			if(!u.getKeys().getPublicKey().toString(16).equals(receiverKey)) {
				return u;
			}
		}
		return null;
	}
	
	private User getReceiverUser(String receiverKey) {
		Search<User> s = new Search<User>(app.getNetwork(), User.class.getSimpleName(), "superPublicKey", true);
		s.search(receiverKey, 3000, 5);
		
		for(User u: s.getResults()) {
			if(u.checkSignature(u.getKeys())) {
				return u;
			}
		}
		return null;
	}
	
	public void launchProtocol(Contrat contrat) {
		String publicKey = um.getCurrentUser().getKeys().getPublicKey().toString(16);
		String receiverPublicKey = contrat.getOtherSignatorie(publicKey);
		User receiver = getReceiverUser(receiverPublicKey);
		User trent = chooseTrent(receiverPublicKey);
		if(trent == null) return; //TODO warning message trent not found
		
		contrat.sign(contrat.getKeys());
		byte[] signature = contrat.getSignature().getBytes();
		Sender s = new Sender(contrat.getKeys());
		Trent t = new Trent(trent.getKeys());
		ResEncrypt resEncrypt = s.Encryption(signature, t.getKey());
		
		Masks mask = s.SendMasksSchnorr();
		BigInteger a = mask.getA();
		BigInteger challenge = s.SendChallenge(mask, resEncrypt.getM());
		
		
		ResponsesSchnorr resSchnorrF = s.SendResponseSchnorrFabric(receiver.getKeys());
		ResponsesCCE resCCEF = s.SendResponseCCEFabric(resEncrypt, t.getKey());
		
		BigInteger c0 = challenge.xor(resCCEF.getChallenge()).xor(resSchnorrF.getChallenge());
		
		BigInteger c = Utils.rand(160, s.getKeys().getP());
		BigInteger c1 = c0.xor(c);
		
		ResponsesCCE resCCE = s.SendResponseCCE(resEncrypt.getM(), t.getKey(),c1);
		
		Response response = new Response(Response.Type.SENDER_SEND_PROOF);
		response.setReceiver(receiverPublicKey);
		response.setTrent(trent);
		response.setSender(app.getManager().getUserManager().getCurrentUser());
		response.setRes(resEncrypt);
		
		sender.sendMessage(response.toString(), this.getServiceName(), null); //todo find peerID;
	}
	
	protected void receiveProof(Response response) {
		String receiver = response.getReceiver();
		ResponsesCCE responseSenderCCE = response.getResponse();
		User trent = response.getTrent();
		User sender = response.getSender();
		User receiverUser = app.getManager().getUserManager().getUser(receiver);
		ResEncrypt res = response.getRes();
		
		Receiver r = new Receiver();
		
		if(r.Verifies(responseSenderCCE, trent.getKeys(), res)) {
			//ok, send signature.
		}
		
	}
	
	protected void sendSign(Response response) {
		
	}

	@Override
	public String getServiceName() {
		return this.getClass().getName();
	}

	@Override
	public Object handleMessage(Message m) {
		Response r = new Response(new String(m.getMessageElement("content").getBytes(true)));
		switch(r.getType()) {
		case SENDER_SEND_PROOF: receiveProof(r);
			break;
		case SEND_SIGN: sendSign(r);
			break;
		default:
			break;
		
		}
		return null; //unused
	}

	@Override
	public void sendMessage(Object data, PeerID... ids) {
		// TODO Auto-generated method stub
		
	}
	
}
