package model.user;

import java.math.BigInteger;

import model.advertisement.AbstractAdvertisement;
import model.advertisement.AdvertisementInstaciator;
import net.jxta.document.AdvertisementFactory;

import org.jdom2.Element;

import util.Hasher;
import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import util.secure.Serpent;

/**
 * This class can be instantiated for contains an user.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 * @author Michael
 * @author Julien Prudhomme
 *
 */
public class User extends AbstractAdvertisement{
	private String nick;			// Friendly user nickname (useless for the system)
	private String hashPwd;			// PassWord is never saved but the hash of the password have to be saved
	private String name;			// The family name of the user
	private String firstName;		// The first name of the user
	private String email;			// The email of the user
	private String phone;			// The phone of the user
	private long date;				// The date of creation/update of the user's profile
	private AsymKeysImpl keys; 		// The public key, user ID on network, is here.
	
	private String clearPassword;	// is never saved, not null only if a user log in this.
	
	/**
	 * To make new User (during registration)
	 * This method will generate new Key
	 * @param nick
	 * @param passWord
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
		this.hashPwd = Hasher.SHA256(passWord);
		this.clearPassword = passWord;				// NEW user, so password saved in clearPassword
		this.name = name;
		this.firstName = firstName; 
		this.email = email;
		this.phone = phone;
		this.date = System.currentTimeMillis();
		this.keys = new AsymKeysImpl(false);
	}
	
	/**
	 * Empty Constructor
	 */
	public User(){ 
		super(); 
	}
	
	/**
	 * Construct a new user based on a XML, well and known formated string.
	 * @param XML
	 */
	public User(String XML) {
		super(XML);
	}

	/**
	 * Construct a new user based on an XML element
	 * @param u
	 */
	public User(Element u) {
		super(u);
	}

	/**
	 * Verifies that the password in parameter corresponds to hashPwd
	 * @param password
	 * @return
	 */
	public boolean isPassword(String password){
		try {
			String hash = Hasher.SHA256(password);
			return hash.equals(this.hashPwd);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Encrypt privateKey with password
	 * @param password - have to be this account's password
	 * @return true if encryption was successful, else false
	 */
	public boolean encryptPrivateKey(String password){
		// Check if Key isn't empty
		if(this.getKeys() == null ||
				this.getKeys().getG() == null ||
				this.getKeys().getP() == null ||
				this.getKeys().getPublicKey() == null ||
				this.getKeys().getPrivateKey() == null){
			System.err.println(this.getAdvertisementName()+" : Key empty !");
			return false;
		}
		// Check if private key is decrypted
		if(!this.getKeys().isCompatible()){
			System.err.println(this.getAdvertisementName()+" : Can't encrypt uncompatible keys !");
			return false;
		}
		BigInteger goodPrivate = this.getKeys().getPrivateKey();
		// Check if the password is good
		if(!this.isPassword(password)){
			System.err.println(this.getAdvertisementName()+" : Wrong password !");
			return false;
		}
		// Try to encrypt
		Serpent cypher = new Serpent(password);
		byte[] privateByte = cypher.encrypt(goodPrivate.toByteArray());
		BigInteger privateKey = new BigInteger(privateByte);
		this.setPrivateKey(privateKey);
		// Check if Keys are compatible
		if(this.getKeys().isCompatible()){
			System.err.println(this.getAdvertisementName()+" : Error during encrypting !");
			this.setPrivateKey(goodPrivate);
			return false;
		}
		return true;
	}
	
	/**
	 * Decrypt the private key with the password
	 * @param password - have to be this account's password
	 * @return true if decryption was successful, else false
	 */
	public boolean decryptPrivateKey(String password){
		// Check if Key isn't empty
		if(this.getKeys() == null ||
				this.getKeys().getG() == null ||
				this.getKeys().getP() == null ||
				this.getKeys().getPublicKey() == null ||
				this.getKeys().getPrivateKey() == null){
			System.err.println(this.getAdvertisementName()+" : Key empty !");
			return false;
		}
		// Check if private key is encrypted
		if(this.getKeys().isCompatible()){
			System.err.println(this.getAdvertisementName()+" : Private key already decrypted !");
			return true;
		}
		BigInteger wrongPrivate = this.getKeys().getPrivateKey();
		// Check if the password is good
		if(!this.isPassword(password)){
			System.err.println(this.getAdvertisementName()+" : Wrong password !");
			return false;
		}
		// Try to decrypt
		Serpent cypher = new Serpent(password);
		byte[] privateByte = cypher.decrypt(wrongPrivate.toByteArray());
		BigInteger privateKey = new BigInteger(privateByte);
		this.setPrivateKey(privateKey);
		// Check if Keys are compatible
		if(!this.getKeys().isCompatible()){
			System.err.println(this.getAdvertisementName()+" : Uncompatible Key !");
			this.setPrivateKey(wrongPrivate);
			return false;
		}
		return true;
	}
	
	//////////// GETTERS \\\\\\\\\\\\\\\\
	public String getNick() {
		return nick;
	}
	
	public String getHashPwd() {
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
	
	public long getDate(){
		return date;
	}
	
	public AsymKeysImpl getKeys(){
		return keys;
	}
	
	public String getClearPwd(){
		return clearPassword;
	}
	
	//////////// SETTERS \\\\\\\\\\\\\\\\
	public void setNick(String login) {
		this.nick = login == null ? "":login;
	}
	
	public void setPassWord(String passWord){
		this.hashPwd = Hasher.SHA256(passWord);
	}
	
	public void setClearPassword(String password) {
		this.clearPassword = password;
	}
	
	public void setHashPwd(String hashPwd) {
		this.hashPwd = hashPwd;
	}
	
	public void setName(String name) {
		this.name = name == null ? "": name;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName == null ? "": firstName;
	}
	
	public void setEmail(String email) {
		this.email = email == null ? "": email;
	}
	
	public void setPhone(String phone) {
		this.phone = phone == null ? "":phone;
	}
	
	public void setDate(long date){
		this.date = date;
	}
	
	public void setKey(AsymKeysImpl key){
		this.keys = key == null ? new AsymKeysImpl(): key;
	}
	
	public void setPrivateKey(BigInteger privateKey){
		this.keys.setPrivateKey(privateKey);
	}
	
	public void setPublicKey(BigInteger publicKey){
		this.keys.setPublicKey(publicKey);
	}
	
	public void setG(BigInteger g){
		this.keys.setG(g);
	}
	
	public void setP(BigInteger p){
		this.keys.setP(p);
	}
	
	public void setClearPwd(String password){
		this.clearPassword = password;
	}
	
	//////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/**
	 * Give the good class to the constructor
	 */
	public static void register() {
		User u = new User();
		AdvertisementFactory.registerAdvertisementInstance(u.getAdvType(),
                										   new AdvertisementInstaciator(u.getClass(), u.getAdvType()));
	}
	
	/**
	 * Used to define Keys and initialize some values
	 */
	@Override
	protected void setKeys() {
		this.keys = new AsymKeysImpl();
		clearPassword = null;
		this.addKey("nick", true);
		this.addKey("hashPwd", false);
		this.addKey("name", false);
		this.addKey("firstName", false);
		this.addKey("email", false);
		this.addKey("phone", false);
		this.addKey("date", false);
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
		this.addValue("hashPwd", this.getHashPwd());
		this.addValue("name", this.getName());
		this.addValue("firstName", this.getFirstName());
		this.addValue("email", this.getEmail());
		this.addValue("phone", this.getPhone());
		this.addValue("date",Long.toString(this.getDate()));
		this.addValue("privateKey", this.keys.getPrivateKey().toString(16));
		this.addValue("publicKey", this.keys.getPublicKey().toString(16));
		this.addValue("p", this.keys.getP().toString(16));
		this.addValue("g", this.keys.getG().toString(16));
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
			setHashPwd(val);
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
		case "date":
			setDate(Long.parseLong(val));
			return true;
		case "key":									// Not used for now, can be used if XML format is changed
			boolean all = true;
			for(Element f: e.getChildren()) {
				all &= handleElement(f);
			}
			return all;
		case "privateKey":
			setPrivateKey(new BigInteger(val, 16));
			return true;
		case "publicKey":
			BigInteger valBigInt = new BigInteger(val,16);
			setPublicKey(valBigInt);
			return true;
		case "p":
			setP(new BigInteger(val, 16));
			return true;
		case "g":
			setG(new BigInteger(val, 16));
			return true;
		default:
			return false;
		}
	}

	////////////////////////////////////////////////// COMPARABLE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * True if keys are the same, false else
	 * @param user
	 * @return
	 */
	public boolean equals(User user) {
		if( (this.keys.getPublicKey() == null || user.keys.getPublicKey() == null)
				&& (this.keys.getPublicKey() != null || user.keys.getPublicKey() != null))
			return false;
		if((this.keys.getP() == null || user.keys.getP() == null)
				&& (this.keys.getP() != null || user.keys.getP() != null))
			return false;
		if((this.keys.getG() == null || user.keys.getG() == null)
				&& (this.keys.getG() != null || user.keys.getG() != null))
			return false;
		if(!(this.keys.getG() != null || user.keys.getG() != null) && 
				(this.keys.getPublicKey().compareTo(user.keys.getPublicKey()) != 0) ||
				!(this.keys.getP() != null || user.keys.getP() != null) && 
				(this.keys.getP().compareTo(user.keys.getP()) != 0) ||
				!(this.keys.getPublicKey() != null || user.keys.getPublicKey() != null) && 
				(this.keys.getG().compareTo(user.keys.getG()) != 0) &&
				this.getDate()==user.getDate())
			return false;
		return true;
	}
	
	
	////////////////////////////////////////////////// MAIN FOR TEST \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public static void main(String[] args){
		User user = new User("nick", "pwd", "name", "firstname", "email", "phone");
		user.sign(user.keys);
		User user2 = new User(user.toString());
		User user3 = new User("nick3", "pwd3", "name", "firstname", "email", "phone");
		
		//System.out.println(user.getPublicKey());
		//System.out.println(user2.getPublicKey());
		
		if(user2.toString().equals(user.toString())) {
			System.out.println("ok");
		}
		
		if(user2.checkSignature(user2.getKeys())) {
			System.out.println("signature ok");
		}
		
		if(!user2.checkSignature(user3.getKeys())) {
			System.out.println("signature ok");
		}
		
		//System.out.println("\n"+user.toString());
		//System.out.println("\n" + user2.toString());
	}
}
