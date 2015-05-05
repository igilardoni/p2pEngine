package model.network.communication;

import java.math.BigInteger;

import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;
import util.Hexa;
import util.secure.AsymKeysImpl;
import util.secure.ElGamal;


/**
 * that class represent a private message between 2 users.
 * The users are represented by their public keys.
 * @author Michael Dubuis
 *
 */
public class Message extends AbstractAdvertisement{
	private BigInteger to;				// This is the public Key (using as login)
	private AsymKeysImpl from;			// This is the AsymKeysImpl of sender (Encrypted with public Key of Owner)
	private String msg;					// This is the content of message (Encrypted with public Key of Owner)
	private long date;					// This is the date of message
	
	/**
	 * This Constructor is used for create a new Message
	 * @param to - AsymKeysImpl of the recipient
	 * @param from - AsymKeysImpl of the sender
	 * @param msg - String content of the message
	 */
	public Message(AsymKeysImpl to, AsymKeysImpl from, String msg){
		super();
		this.to = to.getPublicKey();
		this.from = encryptSender(from, to);
		this.msg = encryptMessage(msg, to);
		this.date = System.currentTimeMillis();
	}
	
	/**
	 * This constructor is used create Message based on a existing message
	 * @param to
	 * @param from
	 * @param msg
	 * @param date
	 */
	public Message(BigInteger to, AsymKeysImpl from, String msg, long date){
		super();
		this.to = to;
		this.from = from;
		this.msg = msg;
		this.date = date;
	}
	
	public Message(String XML){
		super(XML);
	}
	
	public Message(Element e){
		super(e);
	}
	
	//////////////////////////////////////////////////// SETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private void setSenderPublic(BigInteger publicKey){
		this.from.setPublicKey(publicKey);
	}
	private void setSenderP(BigInteger p){
		this.from.setP(p);
	}
	private void setSenderG(BigInteger g){
		this.from.setG(g);
	}
	
	//////////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public String getOwner(){
		return to.toString(16);
	}
	public BigInteger getTo(){
		return to;
	}
	public AsymKeysImpl getSender(AsymKeysImpl key){
		return decryptSender(key);
	}
	public String getMsg(){
		return msg;
	}
	public String getMsg(AsymKeysImpl key){
		return decryptMessage(key);
	}
	public long getDate(){
		return date;
	}
	
	/////////////////////////////////////////////// PRIVATE METHODS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * to encrypt the sender for the owner
	 * @param sender - AsymKeysImpl of the sender
	 * @param owner - AsymKeysImpl of the owner
	 * @return AsymKeysImpl of the sender encrypted with the public Key of the owner
	 */
	private static AsymKeysImpl encryptSender(AsymKeysImpl sender, AsymKeysImpl owner){
		ElGamal eg = new ElGamal(owner);
		BigInteger publicKeySender_Encrypted = new BigInteger(eg.encryptWithPublicKey(sender.getPublicKey().toByteArray()));
		BigInteger pSender_Encrypted = new BigInteger(eg.encryptWithPublicKey(sender.getP().toByteArray()));
		BigInteger gSender_Encrypted = new BigInteger(eg.encryptWithPublicKey(sender.getG().toByteArray()));
		AsymKeysImpl senderKey_Encrypted = new AsymKeysImpl(pSender_Encrypted, gSender_Encrypted, publicKeySender_Encrypted);
		
		return senderKey_Encrypted;
	}
	
	/**
	 * to decrypt the sender's AsymKeysImpl
	 * @param key - AsymKeysImpl of the recipient
	 * @return AsymKeysImpl - the sender's AsymKeysImpl decrypted
	 */
	private AsymKeysImpl decryptSender(AsymKeysImpl key){
		ElGamal eg = new ElGamal(key);
		
		BigInteger publicSender_Decrypted = new BigInteger(eg.decryptWithPrivateKey(from.getPublicKey().toByteArray()));
		BigInteger pSender_Decrypted = new BigInteger(eg.decryptWithPrivateKey(from.getP().toByteArray()));
		BigInteger gSender_Decrypted = new BigInteger(eg.decryptWithPrivateKey(from.getG().toByteArray()));
		AsymKeysImpl senderKey_Decrypted = new AsymKeysImpl(pSender_Decrypted, gSender_Decrypted, publicSender_Decrypted);
		
		return senderKey_Decrypted;
	}
	
	/**
	 * to encrypt the message for the owner 
	 * @param msg - sent string
	 * @param owner - AsymKeysImpl of the owner
	 * @return String - msg encrypted with the public Key of the owner
	 */
	private static String encryptMessage(String msg, AsymKeysImpl owner){
		ElGamal eg = new ElGamal(owner);
		String content_Encrypted = Hexa.bytesToHex(eg.encryptWithPublicKey(msg.getBytes()));
		
		return content_Encrypted;
	}
	
	/**
	 * to decrypt the sent string
	 * @param key - AsymKeysImpl of the recipient
	 * @return String - the sent string decrypted
	 */
	private String decryptMessage(AsymKeysImpl key){
		ElGamal eg = new ElGamal(key);
		String content_Decrypted = Hexa.bytesToHex(eg.decryptWithPrivateKey(Hexa.hexToBytes(msg)));
		
		return content_Decrypted;
	}

	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	protected String getAdvertisementName() {
		return Message.class.getSimpleName();
	}

	@Override
	protected void setKeys() {
		from = new AsymKeysImpl();
		this.addKey("to", true);
		this.addKey("senderPublic", false);
		this.addKey("senderP", false);
		this.addKey("senderG", false);
		this.addKey("msg", false);
		this.addKey("date", true);
	}

	@Override
	protected void putValues() {
		this.addValue("to", to.toString(16));
		this.addValue("senderPublic", from.getPublicKey().toString(16));
		this.addValue("senderP", from.getP().toString(16));
		this.addValue("senderG", from.getG().toString(16));
		this.addValue("msg", msg);
		this.addValue("date", Long.toString(date));
	}

	@Override
	protected boolean handleElement(Element e) {
		String val = e.getText();
		switch(e.getName()){
		case "to":
			this.to = new BigInteger(val, 16);
			return true;
		case "senderPublic":
			setSenderPublic(new BigInteger(val, 16));
			return true;
		case "senderP":
			setSenderP(new BigInteger(val, 16));
			return true;
		case "senderG":
			setSenderG(new BigInteger(val, 16));
			return true;
		case "msg":
			this.msg = val;
			return true;
		case "date":
			this.date = Long.parseLong(val);
			return true;
		default:
			return false;
		}
	}
}
