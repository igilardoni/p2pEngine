package model.communications;

import model.Accord;
import model.Accords;
import model.UsersManagement;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;

public class AccordService extends Service {

	public static final String NEW = "New";
	public static final String ACCEPT = "Accept";
	public static final String DECLINE = "Decline";
	public static final String RATE = "Rate";
	
	private UsersManagement users;
	
	
	public AccordService(UsersManagement users) {
		this.users = users;
	}
	@Override
	public String getServiceName() {
		return getServName();
	}
	
	public static String getServName() {
		return AccordService.class.getName();
	}

	@Override
	protected MessageData handleMessage(Message message) {
		byte[] toBytes = message.getMessageElement("To").getBytes(true);
		byte[] fromBytes = message.getMessageElement("From").getBytes(true);
		byte[] typeBytes = message.getMessageElement("Type").getBytes(true);
		byte[] annonceBytes = message.getMessageElement("Annonce").getBytes(true);
		
		String to = new String(toBytes);
		String from = new String(fromBytes);
		String type = new String(typeBytes);
		String annonce = new String(annonceBytes);
		
		if(type.equals(NEW)) {
			byte[] messageFromBytes = message.getMessageElement("MessageFrom").getBytes(true);
			String messageFrom = new String(messageFromBytes);
			Accords accords = users.getUser(to).getAccords();
			Accord accord = new Accord(from, to, messageFrom, annonce);
			accords.addAccord(accord);
			return new MessageData(from, to, NEW, System.currentTimeMillis());
		}
		else if(type.equals(ACCEPT)) {
			byte[] messageToBytes = message.getMessageElement("MessageTo").getBytes(true);
			String messageTo = new String(messageToBytes);
			Accords accords = users.getUser(to).getAccords();
			Accord accord = accords.getAccord(to, from, annonce);
			accord.setMessageTo(messageTo);
			accords.acceptAccord(accord);
			return new MessageData(from, to, ACCEPT, System.currentTimeMillis());
		}
		else if(type.equals(DECLINE)) {
			Accords accords = users.getUser(to).getAccords();
			accords.declineAccord(to, from, annonce);
			return new MessageData(from, to, DECLINE, System.currentTimeMillis());
		}
		else if(type.equals(RATE)) {
			byte[] rateBytes = message.getMessageElement("Rate").getBytes(true);
			int rate = Integer.parseInt(new String(rateBytes));
			users.getUser(to).addNote(rate);
			Accords accords = users.getUser(to).getAccords();
			Accord accord = accords.getAccord(to, from, annonce);
			accords.rateAccord(accord);
			return new MessageData(from, to, RATE, System.currentTimeMillis());
		}
		
		return null;
	}
	
	public static void sendAccord(Chatter chatter, String from, String to, String annonce, String messageFrom) {
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement(Chatter.toServiceTag, null, getServName().getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("From", null, from.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("To", null, to.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Annonce", null, annonce.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Type", null, NEW.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("MessageFrom", null, messageFrom.getBytes(), null));
		chatter.sendToUser(to, message);
	}
	
	public static void sendAccept(Chatter chatter, String from, String to, String annonce, String messageTo) {
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement(Chatter.toServiceTag, null, getServName().getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("From", null, from.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("To", null, to.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Annonce", null, annonce.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Type", null, ACCEPT.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("MessageTo", null, messageTo.getBytes(), null));
		chatter.sendToUser(to, message);
	}
	
	public static void sendDecline(Chatter chatter, String from, String to, String annonce) {
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement(Chatter.toServiceTag, null, getServName().getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("From", null, from.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("To", null, to.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Annonce", null, annonce.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Type", null, DECLINE.getBytes(), null));
		chatter.sendToUser(to, message);
	}
	
	public static void sendRate(Chatter chatter, String from, String to, String annonce, int rate) {
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement(Chatter.toServiceTag, null, getServName().getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("From", null, from.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("To", null, to.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Annonce", null, annonce.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Type", null, RATE.getBytes(), null));
		message.addMessageElement(new ByteArrayMessageElement("Rate", null, Integer.toString(rate).getBytes(), null));
		chatter.sendToUser(to, message);
	}

}
