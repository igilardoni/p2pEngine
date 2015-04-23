package model.network.communication;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

import util.Hasher;
import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import util.secure.ElGamalSign;
import util.secure.encryptionInterface.AsymEncryption;
import util.secure.encryptionInterface.AsymKeys;
import model.network.Network;
import model.network.NetworkInterface;
import model.network.communication.service.ServiceInterface;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.Message.ElementIterator;
import net.jxta.endpoint.MessageElement;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.protocol.PipeAdvertisement;

/**
 * This class can be bind to an input pipe (specified by the Network) to receive all messages specified to this client.
 * These messages have a specified format, its contain a toService element, then 
 * this class will redirect the message to the specified service, if it exists
 * @author Julien Prudhomme
 *
 */

public class Communication implements PipeMsgListener {
	public final static String SERVICE_TAG = "toService";
	private NetworkInterface network = null;
	private PeerGroup communicationGroup = null;
	private HashMap<String, ServiceInterface> services = new HashMap<String, ServiceInterface>();
	
	/**
	 * Instantiate the Communication class, based on a pipe
	 * provided by the network. Communication should be instantiate once.
	 * @param network an already started network.
	 * @throws Exception 
	 */
	public Communication(NetworkInterface network) throws Exception {
		if(!network.isStarted()) { //Can't correctly instantiate the Communication class if network isn't running.
			throw new Exception("network isn't started");
		}
		
		network.addGroup(this.getClass().getName()); //we add a subgroup reserved for the communications advertisements.
		communicationGroup = network.getGroup(this.getClass().getName());
		this.network = network;
		createInputPipe();
	}
	
	/**
	 * Create a simple advertisement for the pipes' class.
	 * @return
	 */
	private PipeAdvertisement getAdvertisement() {
		return Network.getPipeAdvertisement(IDFactory
				.newPipeID(communicationGroup.getPeerGroupID(), this.getClass().getName().getBytes()), false);
	}
	
	/**
	 * Create an input pipe. All message reveived will be catch in the pipeMsgEvent method.
	 */
	private void createInputPipe() {
		
		PipeAdvertisement pipeAdv = getAdvertisement();
		
		try {
			network.getGroup(this.getClass().getName()).getPipeService().createInputPipe(pipeAdv, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Check the message format. Message received had to contain minimums elements
	 * <toService /> The service aimed by the message
	 * <from /> the publicKey of the message's author
	 * <p></p> p value for the encryption
	 * <g></g> g value for the encryption
	 * <sign /> the signature
	 * @param m
	 * @return
	 */
	private boolean checkMessageFormat(Message m) {
		return
				m.getMessageElement(SERVICE_TAG) != null &&
				m.getMessageElement("from") != null &&
				m.getMessageElement("p") != null &&
				m.getMessageElement("g") != null &&
				m.getMessageElement("signR") != null &&
				m.getMessageElement("signS") != null;
	}
	
	
	/**
	 * Check the message signature according to the "from" public key, and the hash of the entire message.
	 * @param m
	 * @return
	 */
	private boolean checkSignature(Message m) {
		String mConcat = "";
		ElementIterator iterator = m.getMessageElements(); //getting all elements
		
		//We prepare the hash
		while(iterator.hasNext()) {
			MessageElement e = iterator.next();
			if(e.getElementName().equals("sign")) continue; //we will hash all except the signature.
			mConcat = mConcat + new String(e.getBytes(true));
		}
		
		
		//Retrieving public key
		BigInteger publicKey = new BigInteger(m.getMessageElement("from").getBytes(true));
		BigInteger p = new BigInteger(m.getMessageElement("p").getBytes(true));
		BigInteger g = new BigInteger(m.getMessageElement("g").getBytes(true));
		BigInteger r = new BigInteger(m.getMessageElement("signR").getBytes(true));
		BigInteger s = new BigInteger(m.getMessageElement("signS").getBytes(true));
		ElGamal crypter = new ElGamal(); // TODO AsymEncryption Implementation
		
		AsymKeys<BigInteger> keys;
		try {
			keys = new AsymKeysImpl(p, g, publicKey, null);
		} catch (Exception e) {
			//incompatible keys/p/g !
			e.printStackTrace();
			return false;
		}
		crypter.setAsymsKeys(keys);
		
		return crypter.verifySignature(mConcat.getBytes(), new ElGamalSign(r, s));
	}
	
	
	/**
	 * Check if the service exists
	 * @param m the message received
	 * @return true if the service is known and added in the communication module
	 */
	private boolean checkService(Message m) {
		return this.services.containsKey(new String(m.getMessageElement(SERVICE_TAG).getBytes(true)));
	}
	
	/**
	 * Catch all messages aimed for this peer and redirect to the proper
	 * service, if the message's signature is correct. If not, the message is ignored.
	 */
	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		Message m = event.getMessage();
		if(!checkMessageFormat(m)) return; // Message format incorrect, aborting...
		if(!checkSignature(m)) return; // Message signature incorrect, aborting...
		if(!checkService(m)) return; // Service unknown ..
		
		String service = new String(m.getMessageElement(SERVICE_TAG).getBytes(true));
		this.services.get(service).putMessage(m); //sending message to the according service.
	}
	
	/**
	 * Add a service to the communication module
	 * @param service a class implementing ServiceInterface
	 */
	public void addService(ServiceInterface service) {
		services.put(service.getServiceName(), service);
	}

}
