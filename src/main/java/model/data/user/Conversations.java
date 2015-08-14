package model.data.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jdom2.Element;

import util.Hexa;
import util.Printer;
import util.StringToElement;
import util.secure.AsymKeysImpl;
import util.secure.Serpent;
import model.advertisement.AbstractAdvertisement;

/**
 * Conversation of an user.
 * contain messages sent and received.
 * The content of this class is crypted with the owner password.
 * the different message should'nt be crypted individually.
 * @author Julien Prudhomme
 *
 */
public class Conversations extends AbstractAdvertisement{
	/*
	 * String: to (the messages between the user and to)
	 * ArrayList<Message> list of the messages (sent to "to" and reveived from "to")
	 */
	private HashMap<String, ArrayList<UserMessage>> messages; //all message when cypher is decrypted.
	private String owner; //public key of the conversation owner;
	private String cypher;
	private String password = null; //content locked if password is null
	private AsymKeysImpl keys = null;
	
	public Conversations(Element child) {
		super(child);
	}

	public Conversations(User owner) {
		super();
		this.owner = owner.getKeys().getPublicKey().toString(16);
		this.password = owner.getClearPwd();
		this.keys = owner.getKeys();
		setKeys(owner.getKeys());
	}

	@Override
	protected String getAdvertisementName() {
		return this.getClass().getName();
	}

	@Override
	protected void setKeys() {
		messages = new HashMap<String, ArrayList<UserMessage>>();
		addKey("cypher", false, true);
		addKey("owner", true, false);
	}

	@Override
	protected void putValues() {
		addValue("owner", owner);
		
		if(password != null) { //if password is set we can recompute the cypher with the possible new values.
			if(messages == null) {
				System.err.println("password is set but messages still null ?");
				return;
			}
			StringBuffer s = new StringBuffer();
			for(Entry<String, ArrayList<UserMessage>> entry : messages.entrySet()) {
				s.append("<Messages to=\"" + entry.getKey() + "\">");
				for(UserMessage m: entry.getValue()) {
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
			cypher = Hexa.bytesToHex(crypter.encrypt(s.toString().getBytes()));
		}
		addValue("cypher", cypher);
	}

	/**
	 * Unlock conversation content with the right loguedUser.
	 * @param loguedUser
	 */
	public void unLock(User loguedUser) {
		this.password = loguedUser.getClearPwd();
		Serpent crypter = new Serpent(password);
		String clearText = new String(crypter.decrypt(Hexa.hexToBytes(cypher)));
		Element root = StringToElement.getElementFromString(clearText, "UserConversation");
		parseRootElement(root);
	}
	
	public void lock() {
		this.password = null;
		messages = null;
	}
	
	
	/**
	 * We parse the root element, result of decrypted cypher.
	 * @param root 
	 */
	private void parseRootElement(Element root) {
		messages = new HashMap<String, ArrayList<UserMessage>>();
		for(Element e: root.getChildren()) {
			String pkey = e.getAttributeValue("to");
			messages.put(pkey, new ArrayList<UserMessage>());
			for(Element m: e.getChildren()) {
				messages.get(pkey).add(new UserMessage(m));
			}
		}
	}
	
	/**
	 * Get the conversation between the user and "to"
	 * All the message has to be get or added here.
	 * @param to
	 */
	public ArrayList<UserMessage> getConversation(String to) {
		if(password == null) return null; //content locked
		if(!messages.containsKey(to)) messages.put(to, new ArrayList<UserMessage>());
		return messages.get(to);
	}
	
	public ArrayList<String> getSenders(){
		if(password == null) return null; //content locked
		ArrayList<String> senders = new ArrayList<String>();
		for (String sender : messages.keySet()) {
			senders.add(sender);
		}
		return senders;
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
	
	public void addMessage(UserMessage message){
		if(message.isEncrypted()) {
			message.decrypt(keys);
		}
		if(!message.checkSignature(message.getSender())){
			Printer.printError(this, "addMessage", "Bad Signature for message");
			return;
		}
		if(message.getSender().getPublicKey().equals(keys.getPublicKey())) {
			if(!messages.containsKey(message.getReceiver().getPublicKey().toString(16)))
				messages.put(message.getReceiver().getPublicKey().toString(16), new ArrayList<UserMessage>());
			messages.get(message.getReceiver().getPublicKey().toString(16)).add(message);
		} else {
			if(!messages.containsKey(message.getSender().getPublicKey().toString(16)))
				messages.put(message.getSender().getPublicKey().toString(16), new ArrayList<UserMessage>());
			messages.get(message.getSender().getPublicKey().toString(16)).add(message);
		}
	}

	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}
}
