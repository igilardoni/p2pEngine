package model.network.communication.service;

import java.util.ArrayList;

import model.network.communication.Communication;
import net.jxta.endpoint.Message;

/**
 * Base service for Communication, handling message and notifying listeners.
 * @author Julien Prudhomme
 *
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

}
