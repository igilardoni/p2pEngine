package model.network.communication.service;

import net.jxta.endpoint.Message;

/**
 * Interface for some class that want's to know a service receive a message.
 * @author Julien
 *
 */
public interface ServiceListener {
	
	/**
	 * Will be call if a service that's listen receive a message, after processing.
	 * @param m The message
	 */
	public void messageEvent(Message m);
}
