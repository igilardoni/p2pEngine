package model.communications;

import java.util.ArrayList;

import net.jxta.endpoint.Message;

/**
 * Pattern de base pour les services de chatter.
 * Chatter envoit les message recu au service correspondant. Les services traitent ces messages.
 * Les services de chatter devrait etendre cette classe
 * @author Prudhomme Julien
 *
 */
public abstract class Service implements MessageService{

	private ArrayList<MessageServiceListener> listeners = new ArrayList<MessageServiceListener>();
	
	@Override
	public abstract String getServiceName();
	
	public void addListener(MessageServiceListener l) {
		listeners.add(l);
	}
	
	/**
	 * Notifie les eventuels listeners qu'un message a été recu et traité sur ce service
	 * @param msg
	 */
	public void notifyListeners(MessageData msg) {
		for(MessageServiceListener l: listeners) {
			l.messageEvent(msg);
		}
	}
	
	/**
	 * Chatter envoit/route les message ici
	 */
	public void putMessage(Message message) {
		MessageData msg = handleMessage(message);
		if(msg != null) notifyListeners(msg);
	}
	
	/**
	 * C'est ici qu'on traitera les messages
	 * @param message
	 * @return
	 */
	protected abstract MessageData handleMessage(Message message);

}
