package model.network.communication;

import model.network.NetworkInterface;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;

/**
 * This class can be bind to an input pipe (specified by the Network) to receive all messages specified to this client.
 * These messages have a specified format, its contain a toService element, then 
 * this class will redirect the message to the specified service, if it exists
 * @author Julien
 *
 */

public class Communication implements PipeMsgListener {
	public final static String SERVICE_TAG = "toService";
	
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
	}
	
	
	
	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		// TODO Auto-generated method stub
		
	}

}
