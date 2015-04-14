package model.user;

import java.math.BigInteger;

import util.Hasheur;

/* TODO
 * A voir pour la partie des cl� (peut etre dans un autre objet ?)
 * Je crois qu'on aura plus besoin du peerID.
 */

public class User {
	private String login;
	private String hashPwd;
	private String name;
	private String firstName;
	private String email;
	private String phone;
	private BigInteger privateKey;
	private BigInteger publicKey;
	private String peerID;
	
	/**
	 * To edit existing users in the XML file
	 * @param login
	 * @param password
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 * @param privateKey
	 * @param publicKey
	 * @param peerID
	 */
	public User(String login,String password,String name,
			String firstName,String email,
			String phone,BigInteger privateKey, BigInteger publicKey,String peerID
			){
		
		this.login = login;
		this.hashPwd = password;
		this.name = name;
		this.firstName = firstName; 
		this.email = email;
		this.phone = phone;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.peerID = peerID;
	}
	
	/**
	 * To make new User (during registration)
	 * @param login
	 * @param password
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 * @param peerID
	 */
	public User(String login,String password,String name,
			String firstName,String email,
			String phone,String peerID
			){
		
		this.login = login;
		this.hashPwd = password;
		this.name = name;
		this.firstName = firstName; 
		this.email = email;
		this.phone = phone;
		// Generation des clés public et privée ICI
		this.peerID = peerID;
	}
	
	/**
	 * Verifies that the password in parameter corresponds to hashPwd
	 * @param password
	 * @return
	 */
	public boolean isPassword(String password){
		try {
			String hash = Hasheur.SHA256(password);
			if(hash.equals(this.hashPwd))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//////////// GETTERS \\\\\\\\\\\\\\\\
	public String getLogin() {
		return login;
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
	public BigInteger getPrivateKey() {
		return privateKey;
	}
	public BigInteger getPublicKey() {
		return publicKey;
	}
	public String getId_peer() {
		return peerID;
	}
	
	//////////// SETTERS \\\\\\\\\\\\\\\\
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.hashPwd = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setId_peer(String id_peer) {
		this.peerID = id_peer;
	}
}
