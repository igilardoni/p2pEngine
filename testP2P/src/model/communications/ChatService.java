package model.communications;

import java.util.ArrayList;

import model.User;
import model.UsersManagement;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;

public class ChatService implements MessageService {

	private UsersManagement users;
	private ArrayList<ChatServiceListener> listeners = new ArrayList<ChatServiceListener>();
	
	public ChatService(UsersManagement users) {
		this.users = users;
	}
	
	public void addListener(ChatServiceListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public String getServiceName() {
		return getServName();
	}
	
	public static String getServName() {
		return ChatService.class.getName();
	}
	
	private void notifyListeners(MessageData msg) {
		for(ChatServiceListener l: listeners) {
			l.messageEvent(msg);
		}
	}

	@Override
	public void putMessage(Message message) {
		byte[] contentBytes = message.getMessageElement("Content").getBytes(true);
		byte[] toBytes = message.getMessageElement("To").getBytes(true);
		byte[] fromBytes = message.getMessageElement("From").getBytes(true);
		byte[] dateBytes = message.getMessageElement("Date").getBytes(true);
		
		String content = new String(contentBytes);
		String to = new String(toBytes);
		String from = new String(fromBytes);
		long date = new Long(new String(dateBytes));
		
		User user = users.getUser(to);
		if(user != null) {
			MessageData messageData = new MessageData(from, to, content, date);
			user.getMessages().addMessage(messageData, from);
			notifyListeners(messageData);
		}
		
		System.out.println("Recu de " + from + ": " + content);
		
	}
	
	public static boolean sendMessage(Chatter chatter, String from, String to, String content) {
		if(from == null) System.out.println("from null");
		if(to == null) System.out.println("to null");
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement("From", null, from.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("To", null, to.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Content", null, content.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Date", null, Long.toString(System.currentTimeMillis()).getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement(Chatter.toServiceTag, null, getServName().getBytes(), null));
		return chatter.sendToUser(to, message);
	}

}
