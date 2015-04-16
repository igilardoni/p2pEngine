package model.network.communication;

import java.io.IOException;

import model.network.Network;
import model.network.NetworkInterface;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.protocol.PipeAdvertisement;

/**
 * This class can be bind to an input pipe (specified by the Network) to receive all messages specified to this client.
 * These messages have a specified format, its contain a toService element, then 
 * this class will redirect the message to the specified service, if it exists
 * @author Julien
 *
 */

public class Communication implements PipeMsgListener {
	public final static String SERVICE_TAG = "toService";
	private NetworkInterface network = null;
	private PeerGroup communicationGroup = null;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		// TODO Auto-generated method stub
		
	}

}
