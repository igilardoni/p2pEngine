package model.communications;

import java.util.ArrayList;
import java.util.HashMap;



public class UserMessages {

	/**
	 * String : from
	 * ArrayList : les messages
	 */
	private HashMap<String, ArrayList<MessageData>> messages = new HashMap<String, ArrayList<MessageData>>();
	
	public void addMessage(MessageData message, String from) {
		if(!messages.containsKey(from)) {
			messages.put(from, new ArrayList<MessageData>());
		}
		messages.get(from).add(message);
	}
	
	public ArrayList<MessageData> getMessages(String from) {
		return messages.get(from);
	}
	
}
