package network.api;

import java.io.IOException;
import java.util.Collection;

/**
 * Interface for the whole peer
 * @author Julien Prudhomme
 *
 */
public interface Peer {
	/**
	 * Starts the peer
	 * @param cache cache folder
	 * @param port listening port
	 * @throws IOException
	 */
	public void start(String cache, int port) throws IOException;
	
	/**
	 * Stop the server
	 */
	public void stop();
	
	/**
	 * Should return this Peer public IP address
	 * @return a String that represent an IP address
	 */
	public String getIp();
	
	/**
	 * Get a collection of all services supported by this peer
	 * @return
	 */
	public Collection<Service> getServices();
	
	/**
	 * Get a service by its name
	 * @param name The service's name
	 * @return The Service
	 */
	public Service getService(String name);
	
	/**
	 * Add a service to this Peer
	 * @param service
	 */
	public void addService(Service service);
}
