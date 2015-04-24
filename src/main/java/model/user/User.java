package model.user;

import java.math.BigInteger;

import model.advertisement.AbstractAdvertisement;

import org.jdom2.Element;

import util.Hasher;
import util.secure.AsymKeysImpl;
import util.secure.Serpent;

/**
 * This class can be instantiated for contains an user.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 * @author Michael
 *
 */
public class User extends AbstractAdvertisement implements Comparable<User>{
	private String nick = null;
	private String hashPwd = null;
	private String name = null;
	private String firstName = null;
	private String email = null;
	private String phone = null;
	private AsymKeysImpl key = new AsymKeysImpl();
	
	/**
	 * To edit existing users in the XML file
	 * @param nick
	 * @param hashPwd
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 * @param publicKey
	 * @param p
	 * @param g
	 */
	public User(String nick,String hashPwd,String name,
			String firstName,String email,
			String phone,BigInteger publicKey,
			BigInteger p, BigInteger g
			){
		super();
		this.nick = nick;
		this.hashPwd = hashPwd;
		this.name = name;
		this.firstName = firstName; 
		this.email = email;
		this.phone = phone;
		this.key = new AsymKeysImpl();
		key.setP(p);
		key.setG(g);
		key.setPublicKey(publicKey);
		key.setPrivateKey(null);
		setKeys();
	}
	
	/**
	 * To edit existing user
	 * @param nick
	 * @param hashPwd
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 * @param key
	 */
	public User(String nick,String hashPwd,String name,
			String firstName,String email,
			String phone,AsymKeysImpl key
			){
		super();
		this.nick = nick;
		this.hashPwd = hashPwd;
		this.name = name;
		this.firstName = firstName; 
		this.email = email;
		this.phone = phone;
		this.key = key;
		setKeys();
	}
	
	/**
	 * To make new User (during registration)
	 * This method will generate new Key
	 * @param nick
	 * @param hashPwd
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 * @param peerID
	 */
	public User(String nick,String passWord,String name,
			String firstName,String email,
			String phone
			){
		super();
		this.nick = nick;
		try {
			this.hashPwd = Hasher.SHA256(passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.name = name;
		this.firstName = firstName; 
		this.email = email;
		this.phone = phone;
		this.key = new AsymKeysImpl(false);
		setKeys();
	}
	/**
	 * Empty Constructor
	 */
	public User(){ super(); }
	
	/**
	 * Construct a new user based on a XML, well and known formated string.
	 * @param XML
	 */
	public User(String XML) {
		super(XML);
	}

	/**
	 * Verifies that the password in parameter corresponds to hashPwd
	 * @param password
	 * @return
	 */
	public boolean isPassword(String password){
		try {
			String hash = Hasher.SHA256(password);
			if(hash.equals(this.hashPwd))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//////////// GETTERS \\\\\\\\\\\\\\\\
	public String getNick() {
		return nick;
	}
	public String getPassword() {
		return hashPwd;
	}
	public String getName() {
		return name;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public AsymKeysImpl getKey(){
		return key;
	}
	public BigInteger getPublicKey(){
		return key.getPublicKey();
	}
	public BigInteger getPrivateKey(){
		return key.getPrivateKey();
	}
	public BigInteger getP(){
		return key.getP();
	}
	public BigInteger getG(){
		return key.getG();
	}
	
	//////////// SETTERS \\\\\\\\\\\\\\\\
	public void setNick(String login) {
		if(login==null)
			this.nick = "";
		else
			this.nick = login;
	}
	public void setPassword(String password) {
		this.hashPwd = password;
	}
	public void setName(String name) {
		if(name == null)
			this.name = "";
		else
			this.name = name;
	}
	public void setFirstName(String firstName) {
		if(firstName == null)
			this.firstName = "";
		else
			this.firstName = firstName;
	}
	public void setEmail(String email) {
		if(email == null)
			this.email = "";
		else
			this.email = email;
	}
	public void setPhone(String phone) {
		if(phone == null)
			this.phone = "";
		else
			this.phone = phone;
	}
	public void setKey(AsymKeysImpl key){
		if(key == null)
			this.key = new AsymKeysImpl();
		else
			this.key = key;
	}
	public void setPrivateKey(BigInteger privateKey){
		this.key.setPrivateKey(privateKey);
	}
	public void setPublicKey(BigInteger publicKey){
		this.key.setPublicKey(publicKey);
	}
	public void setG(BigInteger g){
		this.key.setG(g);
	}
	public void setP(BigInteger p){
		this.key.setP(p);
	}
	
	//////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Used to define Keys
	 */
	@Override
	protected void setKeys() {
		this.addKey("nick", false);
		this.addKey("hashPwd", false);
		this.addKey("name", false);
		this.addKey("firstName", false);
		this.addKey("email", false);
		this.addKey("phone", false);
		this.addKey("privateKey", false);
		this.addKey("publicKey", true);
		this.addKey("p", false);
		this.addKey("g", false);
	}

	/**
	 * Used to add all keys
	 */
	@Override
	protected void putValues() {
		this.addValue("nick", this.getNick());
		this.addValue("hashPwd", this.getPassword());
		this.addValue("name", this.getName());
		this.addValue("firstName", this.getFirstName());
		this.addValue("email", this.getEmail());
		this.addValue("phone", this.getPhone());
		this.addValue("privateKey", this.getPrivateKey().toString(16));
		this.addValue("publicKey", this.getPublicKey().toString(16));
		this.addValue("p", this.getP().toString(16));
		this.addValue("g", this.getG().toString(16));
	}

	@Override
	protected String getAdvertisementName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected boolean handleElement(org.jdom2.Element e) {
		String val = e.getText();
		switch(e.getName()){
		case "nick":
			setNick(val);
			return true;
		case "hashPwd":
			setPassword(val);
			return true;
		case "name":
			setName(val);
			return true;
		case "firstName":
			setFirstName(val);
			return true;
		case "email":
			setEmail(val);
			return true;
		case "phone":
			setPhone(val);
			return true;
		case "key":									// Not used for now, can be used if XML format is changed
			boolean all = true;
			for(Element f: e.getChildren()) {
				all &= handleElement(f);
			}
			return all;
		case "privateKey":
			setPrivateKey(new BigInteger(val.getBytes()));
			return true;
		case "publicKey":
			BigInteger valBigInt = new BigInteger(val,16);
			setPublicKey(valBigInt);
			return true;
		case "p":
			setP(new BigInteger(val.getBytes())); // TODO Peut etre qu'il faut convertir le hex en d�cimal ? :)
			return true;
		case "g":
			setG(new BigInteger(val.getBytes()));
			return true;
		default:
			return false;
		}
	}

	////////////////////////////////////////////////// COMPARABLE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * @return boolean 0 if both are identical, 1 else
	 */
	@Override
	public int compareTo(User user) {
		if( (this.getPublicKey() == null || user.getPublicKey() == null)
				&& (this.getPublicKey() != null || user.getPublicKey() != null))
			return 1;
		if((this.getP() == null || user.getP() == null)
				&& (this.getP() != null || user.getP() != null))
			return 1;
		if((this.getG() == null || user.getG() == null)
				&& (this.getG() != null || user.getG() != null))
			return 1;
		if(!(this.getG() != null || user.getG() != null) && 
				(this.getPublicKey().compareTo(user.getPublicKey()) != 0) ||
				!(this.getP() != null || user.getP() != null) && 
				(this.getP().compareTo(user.getP()) != 0) ||
				!(this.getPublicKey() != null || user.getPublicKey() != null) && 
				(this.getG().compareTo(user.getG()) != 0))
			return 1;
		return 0;
	}
	
	
	////////////////////////////////////////////////// MAIN FOR TEST \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public static void main(String[] args){
		User user = new User("nick", "pwd", "name", "firstname", "email", "phone");
		Serpent s = new Serpent("pwd");
		// Encryption Private Key
		BigInteger newKey = new BigInteger(s.encrypt(user.getPrivateKey().toByteArray()));
		user.setPrivateKey(newKey);
		
		// Document -> String
		/*Document document = user.getDocument();
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xmlString = outputter.outputString(document);
        System.out.println(xmlString);
		System.out.println(); */
		System.out.println(user.toString() + "\n");
       /* try{
        	// String -> Document 
        	SAXBuilder saxBuilder=new SAXBuilder();
            Reader stringReader=new StringReader(xmlString);
            Document document2=saxBuilder.build(stringReader);
            
            // Document -> String (just for see if same r
            XMLOutputter ouputter2 = new XMLOutputter(Format.getPrettyFormat());
            String xmlString2 = ouputter2.outputString(document2);
            System.out.println(xmlString2);
        }catch(Exception e){
        	e.printStackTrace();
        } */
		
		User user2 = new User(user.toString());
		System.out.println(user2 + "\n");
        
        System.out.println();
        System.out.println(new BigInteger(s.decrypt(newKey.toByteArray())));
	}
}
