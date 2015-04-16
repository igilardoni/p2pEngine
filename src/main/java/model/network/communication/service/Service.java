package model.network.communication.service;

import java.util.ArrayList;
import net.jxta.endpoint.Message;

/**
 * Base service, handling message and notifying listeners
 * @author Julien
 *
 */
public abstract class Service implements ServiceInterface {

	private ArrayList<ServiceListener> listeners = new ArrayList<ServiceListener>(); //listeners list
	
	@Override
	public void putMessage(Message m) {
		if(handleMessage(m)) notifyListeners(m);
	}
	/**
	 * Add a new listener for this service.
	 * @param l A listener.
	 */
	public void addListener(ServiceListener l) {
		listeners.add(l);
	}
	
	/**
	 * Send the event to all listeners.
	 * @param m
	 */
	private void notifyListeners(Message m) {
		for(ServiceListener l : listeners) {
			l.messageEvent(m);
		}
	}
	
	/**
	 * Process the message
	 * @param m the message
	 * @return true if the message was correctly processed
	 */
	public abstract boolean handleMessage(Message m);

}
