package model.communications;

/**
 * Les classes qui ont besoin de savoir qu'un message a été recu doivent implémenté cette interface
 * @author Prudhomme Julien
 *
 */
public interface MessageServiceListener {
	/**
	 * Un message a été recu.
	 * @param msg le message
	 */
	public void messageEvent(MessageData msg);
}
