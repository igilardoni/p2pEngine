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

import java.util.ArrayList;

import model.network.NetworkInterface;
import model.network.communication.Communication;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

/**
 * Base service for Communication, handling message and notifying listeners.
 * @author Julien Prudhomme
 * @param D Data to pafnaif
 */
public abstract class Service<D> implements ServiceInterface {

	private ArrayList<ServiceListener<D>> listeners = new ArrayList<ServiceListener<D>>(); //listeners list
	protected Communication sender;
	
	@Override
	public void putMessage(Message m) {
		D res = handleMessage(m);
		if(res != null) notifyListeners(res);
	}
	/**
	 * Add a new listener for this service.
	 * @param l A listener.
	 */
	public void addListener(ServiceListener<D> l) {
		listeners.add(l);
	}
	
	/**
	 * Send the event to all listeners.
	 * @param m
	 */
	private void notifyListeners(D m) {
		for(ServiceListener<D> l : listeners) {
			l.messageEvent(m);
		}
	}
	
	/**
	 * Process the message
	 * @param m the message
	 * @return true if the message was correctly processed
	 */
	public abstract D handleMessage(Message m);
	
	@Override
	public void setCommunication(Communication c) {
		this.sender = c;
	}
	
	public NetworkInterface getNetwork() {
		return sender.getNetwork();
	}
	
	public abstract void sendMessage(D data, PeerID ...ids);

}
