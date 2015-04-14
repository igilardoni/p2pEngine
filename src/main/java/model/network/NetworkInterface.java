package model.network;

import net.jxta.peergroup.PeerGroup;

/**
 * Network interface for groups and scope
 * @author Julien
 *
 */
public interface NetworkInterface {
	
	
	/**
	 * Get the mentioned group
	 * @param group The group name
	 * @return a PeerGroup registered with the name group, or null if it doesn't exist.
	 */
	public PeerGroup getGroup(String group);
	
	
	/**
	 * Add a new PeerGroup on the network.
	 * @param name The new PeerGroup's name
	 */
	public void addGroup(String name);
	
	
	/**
	 * Starts the network
	 */
	public void start();
	
	
	/**
	 * Stop the network
	 */
	public void stop();
}
