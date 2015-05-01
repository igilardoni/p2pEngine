package model.network.communication;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import model.network.Network;
import model.network.NetworkInterface;
import model.network.communication.service.Service;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.OutputPipe;
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
	@SuppressWarnings("rawtypes")
	private HashMap<String, Service> services = new HashMap<String, Service>();
	
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
	
	@SuppressWarnings("rawtypes")
	public Service getService(String name) {
		return services.get(name);
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
				m.getMessageElement("content") != null;
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
		if(!checkService(m)) return; // Service unknown ..
		
		String service = new String(m.getMessageElement(SERVICE_TAG).getBytes(true));
		this.services.get(service).putMessage(m); //sending message to the according service.
	}
	
	/**
	 * Add a service to the communication module
	 * @param service a class implementing ServiceInterface
	 */
	public void addService(Service<?> service) {
		service.setCommunication(this);
		services.put(service.getServiceName(), service);
	}
	
	
	private Message createMessage(String toService, String content) {
		Message m = new Message();
		m.addMessageElement(new ByteArrayMessageElement(SERVICE_TAG, null, toService.getBytes(), null));
		m.addMessageElement(new ByteArrayMessageElement("content", null, content.getBytes(), null));
		return m;
	}
	
	/**
	 * Sends a message to one or severals peers.
	 * @param message the message content.
	 * @param ids the peers' PeerID.
	 * @return true if the message is sended.
	 */
	public boolean sendMessage(String content, String toService, PeerID ...ids) {
		HashSet<PeerID> to = new HashSet<PeerID>();
		OutputPipe pipe = null;
		for(PeerID id: ids) {
			to.add(id);
		}
		try {
			pipe = communicationGroup.getPipeService().createOutputPipe(getAdvertisement(), to, 10000);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			pipe.send(createMessage(toService, content));
		} catch (IOException e) {
			e.printStackTrace();
			pipe.close();
			return false;
		}
		
		pipe.close();
		return true;
	}

}
