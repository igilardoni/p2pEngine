package p2pEngine;

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

public class Utilisateur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5752174219173768047L;
	/**
	 * 
	 */
	private String nom;
	private String tel;
	private String mail;
	private String password;
	
	
	public Utilisateur(String nom, String tel, String mail) {
		this.nom = nom;
		this.tel = tel;
		this.mail = mail;
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
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * On sauvegarde sur le dd !
	 * @param filename adresse
	 */
	public void serialiser(String filename) {
		ObjectOutputStream oos = null;
		FileOutputStream fichier;
		try {
			fichier = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * On crée une instance à partir d'un fichier
	 * @param filename adresse
	 * @return
	 */
	public static Utilisateur deserialiser(String filename) {
		ObjectInputStream ois = null;
		
		FileInputStream fichier;
		try {
			fichier = new FileInputStream(filename);
			ois = new ObjectInputStream(fichier);
			Utilisateur user = (Utilisateur) ois.readObject();
			ois.close();
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Utilisateur("Nom", "XX.XX.XX.XX.XX", "nom@host.com"); //si erreur on envoit l'utilisateur par defaut
		
	}
}
