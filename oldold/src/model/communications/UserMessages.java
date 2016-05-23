package model.communications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;



public class UserMessages implements Serializable{

	private static final long serialVersionUID = 7962612078566738863L;
	/**
	 * String : from
	 * ArrayList : les messages
	 */
	private HashMap<String, MessagesContainer> messages = new HashMap<String, MessagesContainer>();
	
	class MessagesContainer implements Serializable{
		private static final long serialVersionUID = 8278905937746761919L;
		public int newMessage = 0;
		public ArrayList<MessageData> msgs = new ArrayList<MessageData>();
	}
	
	public UserMessages() {
		for(String s: messages.keySet()) {
			System.out.println(s);
		}
	}
	
	public ArrayList<String> getConvers() {
		ArrayList<String> list = new ArrayList<String>();
		for(String s: messages.keySet()) {
			list.add(s);
		}
		Collections.sort(list);
		return list;
	}
	
	public void newConversation(String from) {
		messages.put(from, new MessagesContainer());
	}
	
	public void addMessage(MessageData message, String from) {
		if(!messages.containsKey(from)) {
			newConversation(from);
		}
		messages.get(from).msgs.add(message);
		messages.get(from).newMessage++;
	}
	
	public ArrayList<MessageData> getMessages(String from) {
		if(messages.get(from) == null) return new ArrayList<MessageData>();
		return messages.get(from).msgs;
	}
	
	public void deleteConvest(String from) {
		messages.remove(from);
	}
	
	public int getNumberNewMsgs() {
		int i = 0;
		for(MessagesContainer c: messages.values()) {
			i += c.newMessage;
		}
		return i;
	}
	
	public void viewUser(String user) {
		if(messages.get(user) != null)
		messages.get(user).newMessage = 0;
	}
	
	public int getNumberUserMessage(String user) {
		return messages.get(user) == null ? 0: messages.get(user).newMessage;
	}
	
}
