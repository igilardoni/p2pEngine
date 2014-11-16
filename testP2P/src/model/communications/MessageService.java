package model.communications;

import net.jxta.endpoint.Message;

/**
 * Tout les services de chatter doivent implémenter cette interface
 * @author Prudhomme Julien
 *
 */
public interface MessageService {
	/**
	 * Le nom du service
	 * @return String qui représente le nom du service
	 */
	public String getServiceName();
	
	/**
	 * Traite un message recu.
	 * @param message
	 */
	public void putMessage(Message message);
}
