package model.communications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Represente les discussions d'un utilisateur
 * @author Prudhomme Julien
 *
 */
public class UserMessages implements Serializable{

	private static final long serialVersionUID = 7962612078566738863L;
	
	
	/**
	 * <String : from
	 * MessageContainer> : les messages
	 */
	private HashMap<String, MessagesContainer> messages = new HashMap<String, MessagesContainer>();
	
	
	
	class MessagesContainer implements Serializable{
		private static final long serialVersionUID = 8278905937746761919L;
		public int newMessage = 0; //le nombre de messages non lu dans cette conversation
		public ArrayList<MessageData> msgs = new ArrayList<MessageData>(); //les messages
	}
	
	/**
	 * Obtien la listes des conversations par ordre alphabétique
	 * @return
	 */
	public ArrayList<String> getConvers() {
		ArrayList<String> list = new ArrayList<String>();
		for(String s: messages.keySet()) {
			list.add(s);
		}
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Créer une nouvelle discution
	 * @param from celui qui envoit ou qui recoit les messages
	 */
	public void newConversation(String from) {
		messages.put(from, new MessagesContainer());
	}
	
	/**
	 * Ajoute un message.
	 * @param message
	 * @param from 
	 */
	public void addMessage(MessageData message, String from) {
		if(!messages.containsKey(from)) {
			newConversation(from);
		}
		messages.get(from).msgs.add(message);
		messages.get(from).newMessage++;
	}
	
	/**
	 * Obtient la liste des messages de from
	 * @param from
	 * @return
	 */
	public ArrayList<MessageData> getMessages(String from) {
		if(messages.get(from) == null) return new ArrayList<MessageData>();
		return messages.get(from).msgs;
	}
	
	/**
	 * Supprime une conversation
	 * @param from
	 */
	public void deleteConvest(String from) {
		messages.remove(from);
	}
	
	/**
	 * Obtient le nombre total de messages non lus
	 * @return
	 */
	public int getNumberNewMsgs() {
		int i = 0;
		for(MessagesContainer c: messages.values()) {
			i += c.newMessage;
		}
		return i;
	}
	
	/**
	 * Marque tout les message d'une conversation avec user
	 * @param user
	 */
	public void viewUser(String user) {
		if(messages.get(user) != null)
		messages.get(user).newMessage = 0;
	}
	
	/**
	 * Obtien le nombre de message non lu de la conversation avec user
	 * @param user
	 * @return
	 */
	public int getNumberUserMessage(String user) {
		return messages.get(user) == null ? 0: messages.get(user).newMessage;
	}
	
}
