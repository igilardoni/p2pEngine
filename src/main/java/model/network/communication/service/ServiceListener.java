package model.network.communication.service;

/**
 * Interface for some class that want's to know a service receive a message.
 * @param <D> The type of data returned
 * @author Julien Prudhomme
 *
 */
public interface ServiceListener<D>{
	
	/**
	 * Will be call if a service that's listen receive a message, after processing.
	 * @param m The message
	 */
	public void messageEvent(D m);
}
