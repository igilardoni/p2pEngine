package model.data.user;

import java.math.BigInteger;
import java.util.Collection;

import org.jdom2.Element;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import model.advertisement.AbstractAdvertisement;

public class Message extends AbstractAdvertisement {

	private String subject;
	private String message;
	private AsymKeysImpl sender;
	private String senderName;
	private AsymKeysImpl receiver;
	private long date;
	boolean encrypted = false;
	
	
	
	/**
	 * Create a new message
	 * @param receiver 
	 * @param sender
	 * @param message
	 */
	public Message(AsymKeysImpl receiver, AsymKeysImpl sender, String message) {
		super();
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
		this.date = System.currentTimeMillis();
	}
	
	public Message(String XML) {
		super(XML);
	}
	
	public Message(Element root) {
		super(root);
	}
	
	@Override
	protected String getAdvertisementName() {
		return getClass().getSimpleName();
	}

	
	/**
	 * Encrypt the message. Must be call before the message is send.
	 */
	public void encrypt() {
		if(encrypted) return;
		encrypted = true;
		ElGamal eg = new ElGamal(receiver);
		message = new String(eg.encryptWithPublicKey(message.getBytes()));
		BigInteger crypted = 
				new BigInteger(
						new String(eg.encryptWithPublicKey(sender.getPublicKey().toByteArray())));
		sender.setPublicKey(crypted);
	}
	
	/**
	 * Decrypt the message.
	 * @param receiver the receiver keys (with private key)
	 */
	public void decrypt(AsymKeysImpl receiver) {
		if(!encrypted) return;
		ElGamal eg = new ElGamal(receiver);
		message = new String(eg.decryptWithPrivateKey(message.getBytes()));
		sender.setPublicKey(new BigInteger(
				eg.decryptWithPrivateKey(sender.getPublicKey().toByteArray())));
		encrypted = false;
	}
	
	@Override
	protected void setKeys() {
		sender = new AsymKeysImpl();
		receiver = new AsymKeysImpl();
		
		addKey("content", false, false);
		
		addKey("senderKey", false, false);
		addKey("senderP", false, false);
		addKey("senderG", false, false);
		
		addKey("receiverKey", true, false);
		addKey("receiverP", false, false);
		addKey("receiverG", false, false);
		
		addKey("date", false, false);
		addKey("subject", false, false);
		addKey("senderName", false, false);
	}

	@Override
	protected void putValues() {
		addValue("content", message);
		addValue("senderKey", sender.getPublicKey().toString(16));
		addValue("senderP", sender.getP().toString(16));
		addValue("senderG", sender.getG().toString(16));
		addValue("receiverKey", receiver.getPublicKey().toString(16));
		addValue("receiverP", receiver.getP().toString(16));
		addValue("receiverG", receiver.getG().toString(16));
		addValue("subject", subject);
		addValue("senderName", senderName);
		addValue("date", Long.toString(date));
	}

	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "content": message = e.getValue(); return true;
		case "senderKey": sender.setPublicKey(new BigInteger(e.getValue(), 16)); return true;
		case "senderP": sender.setP(new BigInteger(e.getValue(), 16)); return true;
		case "senderG": sender.setG(new BigInteger(e.getValue(), 16)); return true;
		case "receiverKey": receiver.setPublicKey(new BigInteger(e.getValue(), 16)); return true;
		case "receiverP": receiver.setP(new BigInteger(e.getValue(), 16)); return true;
		case "receiverG": receiver.setG(new BigInteger(e.getValue(), 16)); return true;
		case "date": date =  new Long(e.getValue()); return true;
		}
		return false;
	}
	
	/**
	 * Get if the message is encrypted or not.
	 * @return true if message is encrypted.
	 */
	public boolean isEncrypted() {
		return encrypted;
	}
	
	public AsymKeysImpl getSender() {
		return sender;
	}
	
	public AsymKeysImpl getReceiver() {
		return receiver;
	}

	public long getDate() {
		return date;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getContent() {
		return message;
	}
	

}