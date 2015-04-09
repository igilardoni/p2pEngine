package model.communications;

import model.User;
import model.UsersManagement;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;

public class FriendRequestService extends Service{

	private static String REQUEST = "Request";
	private static String ACCEPT = "Accept";
	private UsersManagement users;
	
	@Override
	public String getServiceName() {
		return getServName();
	}
	
	public FriendRequestService(UsersManagement users) {
		this.users = users;
	}
	
	public static String getServName() {
		return FriendRequestService.class.getName();
	}

	@Override
	protected MessageData handleMessage(Message message) {
		byte[] toBytes = message.getMessageElement("To").getBytes(true);
		byte[] fromBytes = message.getMessageElement("From").getBytes(true);
		byte[] typeBytes = message.getMessageElement("Type").getBytes(true);
		byte[] dateBytes = message.getMessageElement("Date").getBytes(true);
		
		String to = new String(toBytes);
		String from = new String(fromBytes);
		String type = new String(typeBytes);
		long date = new Long(new String(dateBytes));
		
		User user = users.getUser(to);
		if(user != null) {
			MessageData msg = new MessageData(from, to, type, date);
			if(type.equals(REQUEST)) {
				if(!user.getRequests().contains(msg)) {
					user.getRequests().add(msg);
					return msg;
				}
				
			}
			if(type.equals(ACCEPT)) {
				user.acceptRequest(from);
				return msg;
			}
		}
		return null;
	}
	
	public static boolean sendRequest(Chatter chatter, String from, String to) {
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement("From", null, from.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("To", null, to.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Type", null, REQUEST.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Date", null, Long.toString(System.currentTimeMillis()).getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement(Chatter.toServiceTag, null, getServName().getBytes(), null));
		return chatter.sendToUser(to, message);
	}
	
	public static boolean sendAccept(Chatter chatter, String from, String to) {
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement("From", null, from.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("To", null, to.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Type", null, ACCEPT.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Date", null, Long.toString(System.currentTimeMillis()).getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement(Chatter.toServiceTag, null, getServName().getBytes(), null));
		return chatter.sendToUser(to, message);
	}

}
