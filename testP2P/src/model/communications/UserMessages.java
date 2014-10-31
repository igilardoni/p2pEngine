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
	private HashMap<String, ArrayList<MessageData>> messages = new HashMap<String, ArrayList<MessageData>>();
	
	public UserMessages() {
		System.out.println("test :");
		for(String s: messages.keySet()) {
			System.out.println(s);
		}
		System.out.println("fin test");
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
		if(from == null) System.out.println("le probleme viens de la");
		messages.put(from, new ArrayList<MessageData>());
	}
	
	public void addMessage(MessageData message, String from) {
		if(!messages.containsKey(from)) {
			newConversation(from);
		}
		messages.get(from).add(message);
	}
	
	public ArrayList<MessageData> getMessages(String from) {
		return messages.get(from);
	}
	
}
