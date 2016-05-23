/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package model.network;

import net.jxta.peergroup.PeerGroup;

/**
 * Network interface for groups and scope
 * @author Julien Prudhomme
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
	 * Get the default group of the network.
	 * @return the default PeerGroup.
	 */
	public PeerGroup getDefaultGroup();
	
	/**
	 * Add a new PeerGroup on the network.
	 * @param name The new PeerGroup's name
	 */
	public void addGroup(String name);
	
	/**
	 * Add a new PeerGroup on the network
	 * @param name The new PeerGroup's name
	 * @param idGroup add a 2nd PeerGroup, "id-name".
	 */
	public void addGroup(String name, boolean idGroup);
	
	/**
	 * Starts the network
	 */
	public void start();
	
	
	/**
	 * Stop the network
	 */
	public void stop();
	
	
	/**
	 * Should return true only if the network is correctly started.
	 * @return true if the network is running, otherwise false.
	 */
	public boolean isStarted();
	
	/**
	 * Get a valid bootrapable Ip adress
	 * @return a public Ip adresse
	 */
	public String getBootStrapIp();
	
	/**
	 * Boot on an existing network.
	 * @param adress
	 */
	public void boot(String adress);
}
