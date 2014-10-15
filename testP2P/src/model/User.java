package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Represente un utilisateur
 * @author Prudhomme Julien
 *
 */

public class User implements Serializable{

	private static final long serialVersionUID = -4561839847993312221L;
	private String login;
	private String nom;
	private String prenom;
	private String adresse;
	private String tel;
	private String mail;
	private int password; // toujours stoqué sous forme de hash.
	
	
	public User(String nom, String prenom, String adresse, String tel, String mail, String login, String password) {
		this.nom = nom;
		this.tel = tel;
		this.mail = mail;
		this.login = login;
		this.prenom = prenom;
		this.adresse = adresse;
		this.setPassword(password);
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public void setPassword(String password) {
		if(password == null) return;
		this.password = password.hashCode();
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public int getPasswordHash() {
		return this.password;
	}
	
	public boolean checkPassword(String password) {
		return this.password == password.hashCode();
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
}
