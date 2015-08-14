package model.data.user;

import java.math.BigInteger;

import model.advertisement.AbstractAdvertisement;

import org.jdom2.Element;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;

public class UserMessage extends AbstractAdvertisement {

	private String subject;
	private String message;
	private String senderName;
	private AsymKeysImpl receiver;
	private long date;
	private boolean read = false;
	private boolean encrypted = false;
	
	
	
	/**
	 * Create a new message
	 * @param receiver 
	 * @param sender
	 * @param message
	 */
	public UserMessage(AsymKeysImpl receiver, AsymKeysImpl sender, String subject, String message) {
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
	
	@SuppressWarnings("rawtypes")
	public UserMessage(net.jxta.document.Element root) {
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
		ElGamal eg = new ElGamal(receiver);
		message = new String(eg.encryptWithPublicKey(message.getBytes()));
		BigInteger crypted = 
				new BigInteger(
						new String(eg.encryptWithPublicKey(getKeys().getPublicKey().toByteArray())));
		getKeys().setPublicKey(crypted);
		encrypted = true;
	}
	
	/**
	 * Decrypt the message.
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
		//setKeys(new AsymKeysImpl());
		receiver = new AsymKeysImpl();
		
		addKey("content", false, false);
		
		addKey("receiverKey", true, false);
		addKey("read", false, true);
		addKey("date", false, false);
		addKey("subject", false, false);
		addKey("senderName", false, false);
		addKey("encrypted", false, false);
	}

	@Override
	protected void putValues() {
		addValue("content", message);
		addValue("receiverKey", receiver.toString());
		addValue("subject", subject);
		addValue("senderName", senderName);
		addValue("date", Long.toString(date));
		addValue("encrypted", encrypted?"true":"false");
		addValue("read", Boolean.toString(read));
	}

	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "content": message = e.getValue(); return true;
		case "receiverKey": receiver = new AsymKeysImpl(e.getValue()); return true;
		case "date": date =  new Long(e.getValue()); return true;
		case "subject": subject = e.getValue(); return true;
		case "senderName": senderName = e.getValue(); return true;
		case "encrypted": encrypted = (e.getValue().equals("true"))?true:false; return true;
		case "read": read = Boolean.parseBoolean(e.getValue()); return true;
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
	
	public boolean isRead() {
		return read;
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
	
	public void setRead(boolean read) {
		this.read = read;
	}
	
	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}
}