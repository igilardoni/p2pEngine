package model.data.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jdom2.Element;

import util.StringToElement;
import util.secure.AsymKeysImpl;
import util.secure.Serpent;
import model.advertisement.AbstractAdvertisement;

/**
 * Conversation of an user.
 * contain messages sent and received.
 * The content of this class is crypted with the owner password.
 * @author Julien Prudhomme
 *
 */
public class Conversations extends AbstractAdvertisement{
	/*
	 * String: to (the messages between the user and to)
	 * ArrayList<Message> list of the messages (sent to "to" and reveived from "to")
	 */
	private HashMap<String, ArrayList<Message>> messages = null; //all message when cypher is decrypted.
	private String owner; //public key of the conversation owner;
	private String cypher;
	private String password = null; //content locked if password is null
	private AsymKeysImpl keys = null;
	
	public Conversations(Element child) {
		super(child);
	}

	public Conversations(String owner) {
		super();
		this.owner = owner;
	}

	@Override
	protected String getAdvertisementName() {
		return this.getClass().getName();
	}

	@Override
	protected void setKeys() {
		addKey("owner", false, false);
		addKey("cypher", false, true);
	}

	@Override
	protected void putValues() {
		if(password != null) { //if password is set we can recompute the cypher with the possible new values.
			if(messages == null) {
				System.err.println("password is set but messages still null ?");
				return;
			}
			StringBuffer s = new StringBuffer();
			for(Entry<String, ArrayList<Message>> entry : messages.entrySet()) {
				s.append("<Messages to=\"" + entry.getKey() + "\">");
				for(Message m: entry.getValue()) {
					s.append(m.toString());
				}
				s.append("</Messages>");
			}
			
			/*
			 * Proto for XML convers:
			 * <Messages to="anUserPublicKey">
			 * 		<Message>message content</Message>
			 * 		...
			 * </Messages>
			 * <Messages to="anOtherPublicKey">
			 * ...
			 */
			
			Serpent crypter = new Serpent(password);
			cypher = new String(crypter.encrypt(s.toString().getBytes()));
		}
		
		addValue("cypher", cypher);
		addValue("owner", owner);
	}

	/**
	 * Unlock conversation content with the right loguedUser.
	 * @param loguedUser
	 */
	public void unLock(User loguedUser) {
		this.password = loguedUser.getClearPwd();
		this.keys = loguedUser.getKeys();
		Serpent crypter = new Serpent(password);
		String clearText = new String(crypter.decrypt(cypher.getBytes()));
		Element root = StringToElement.getElementFromString(clearText, "UserConversation");
		parseRootElement(root);
	}
	
	public void lock() {
		this.password = null;
		this.keys = null;
		messages = null;
	}
	
	
	/**
	 * We parse the root element, result of decrypted cypher.
	 * @param root 
	 */
	private void parseRootElement(Element root) {
		messages = new HashMap<String, ArrayList<Message>>();
		for(Element e: root.getChildren()) {
			String pkey = e.getAttributeValue("to");
			messages.put(pkey, new ArrayList<Message>());
			for(Element m: e.getChildren()) {
				messages.get(pkey).add(new Message(m));
			}
		}
	}
	
	/**
	 * Get the conversation between the user and "to"
	 * All the message has to be get or added here.
	 * @param to
	 */
	public ArrayList<Message> getConversation(String to) {
		if(password == null) return null; //content locked
		if(!messages.containsKey(to)) messages.put(to, new ArrayList<Message>());
		return messages.get(to);
	}
	
	
	public String getOwner() {
		return owner;
	}
	
	@Override
	protected boolean handleElement(Element e) {
		if(e.getName().equals("cypher")) {
			cypher = e.getValue();
			return true;
		}
		if(e.getName().equals("owner")) {
			owner = e.getValue();
			return true;
		}
		return false;
	}
	
	public void addMessage(Message message, AsymKeysImpl key){
		if(message == null)
			return;
		String sender = message.getSender(key).getPublicKey().toString(16);
		if(!messages.containsKey(sender)) {
			messages.put(sender, new ArrayList<Message>());
		}
		messages.get(sender).add(message);
	}
}
