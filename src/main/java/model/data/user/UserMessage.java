package model.data.user;

import java.math.BigInteger;
import java.util.Collection;

import org.jdom2.Element;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import model.advertisement.AbstractAdvertisement;

public class UserMessage extends AbstractAdvertisement {

	private String subject;
	private String message;
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
	public UserMessage(AsymKeysImpl receiver, AsymKeysImpl sender, String message) {
		super();
		this.message = message;
		setKeys(sender);
		this.receiver = receiver;
		this.date = System.currentTimeMillis();
	}
	
	public UserMessage(String XML) {
		super(XML);
	}
	
	public UserMessage(Element root) {
		super(root);
	}
	
	@Override
	protected String getAdvertisementName() {
		return getClass().getSimpleName();
	}

	
	/**
	 * Encrypt the message. Must be call before the message is send.
	 * TODO A REVOIR
	 */
	public void encrypt() {
		if(encrypted) return;
		encrypted = true;
		ElGamal eg = new ElGamal(receiver);
		message = new String(eg.encryptWithPublicKey(message.getBytes()));
		BigInteger crypted = 
				new BigInteger(
						new String(eg.encryptWithPublicKey(getKeys().getPublicKey().toByteArray())));
		getKeys().setPublicKey(crypted);
	}
	
	/**
	 * Decrypt the message.
	 * TODO A REVOIR
	 * @param receiver the receiver keys (with private key)
	 */
	public void decrypt(AsymKeysImpl receiver) {
		if(!encrypted) return;
		ElGamal eg = new ElGamal(receiver);
		message = new String(eg.decryptWithPrivateKey(message.getBytes()));
		getKeys().setPublicKey(new BigInteger(
				eg.decryptWithPrivateKey(getKeys().getPublicKey().toByteArray())));
		encrypted = false;
	}
	
	@Override
	protected void setKeys() {
		setKeys(new AsymKeysImpl());
		receiver = new AsymKeysImpl();
		
		addKey("content", false, false);
		
		addKey("receiverKey", true, false);
		
		addKey("date", false, false);
		addKey("subject", false, false);
		addKey("senderName", false, false);
	}

	@Override
	protected void putValues() {
		addValue("content", message);
		addValue("receiverKey", receiver.toString());
		addValue("subject", subject);
		addValue("senderName", senderName);
		addValue("date", Long.toString(date));
	}

	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "content": message = e.getValue(); return true;
		case "receiverKey": receiver = new AsymKeysImpl(e.getValue()); return true;
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
		return getKeys();
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