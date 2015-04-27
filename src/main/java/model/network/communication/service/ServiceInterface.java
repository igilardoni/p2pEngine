package model.network.communication.service;

import model.network.communication.Communication;
import net.jxta.endpoint.Message;

/**
 * A service handle messages and process these. Typically, the Communication class
 * will call the putMessage method according to the toService tag of the message.
 * @author Julien Prudhomme
 *
 */
public interface ServiceInterface {
	
	/**
	 * Return the service name
	 * @return A string representing the service
	 */
	public String getServiceName();
	
	/**
	 * Send a message to this service
	 * @param the message
	 */
	public void putMessage(Message m);
	
	/**
	 * Define the communication instance used to send messages.
	 * @param c
	 */
	public void setCommunication(Communication c);
}
