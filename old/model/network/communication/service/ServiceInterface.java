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
