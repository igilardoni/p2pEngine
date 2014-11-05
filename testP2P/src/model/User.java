package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import model.communications.MessageData;
import model.communications.UserMessages;
import model.pdf.ObjetPdfModel;
import model.pdf.ObjetPdfGenerator;
import model.pdf.UserPdfGenerator;
import model.pdf.UserPdfModel;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;

/**
 * Represente un utilisateur
 * @author Prudhomme Julien
 *
 */

public class User extends AbstractAdvertisable implements Serializable{

	private static final long serialVersionUID = -4561839847993312221L;
	private String login;
	private String nom;
	private String prenom;
	private String adresse;
	private String tel;
	private String mail;
	private int password; // toujours stoqué sous forme de hash.
	private Vector<Integer> notes; //toutes les notes donnée par les autres
	private ObjetsManagement objets = new ObjetsManagement();
	private ObjetsManagement panier = new ObjetsManagement();
	private UserMessages messages = new UserMessages();
	private ArrayList<String> friends = new ArrayList<String>();
	private ArrayList<MessageData> requests = new ArrayList<MessageData>();
	
	
	public User(String nom, String prenom, String adresse, String tel, String mail, String login, String password) {
		this.nom = nom;
		this.tel = tel;
		this.mail = mail;
		this.login = login;
		this.prenom = prenom;
		this.adresse = adresse;
		this.notes = new Vector<Integer>();
		this.setPassword(password);
	}
	
	public ArrayList<MessageData> getRequests() {
		return requests;
	}
	
	public void acceptRequest(String user) {
		for(MessageData m: requests) {
			if(m.getSender().equals(user)) {
				requests.remove(m);
				friends.add(user);
				return;
			}
		}
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
	
	public void setPassword(int password) {
		this.password = password;
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
	
	public boolean isPasswordEqual(String password) {
		return this.password == password.hashCode();
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
	
	public ObjetsManagement getObjets() {
		return objets;
	}
	
	public void addNote(int note) {
		if(note < 0 || note > 5) throw new IllegalArgumentException("0 >= note <= 5");
		this.notes.add(note);
	}
	
	public int getMoyenneNotes() {
		if(notes.size() == 0) return 0;
		int add = 0;
		for(Integer i: notes) {
			add += i;
			System.out.println(i);
		}
		return add/notes.size();
	}
	
	public void flushNotes() {
		this.notes.removeAllElements();
	}
	
	public UserPdfModel createModel(){
		return new UserPdfModel(this);
	}
	
	public void createPDF(){
		UserPdfModel model = createModel();
		new UserPdfGenerator(model);
	}
	
	
	public ObjetsManagement getPanier() {
		return this.panier;
	}

	@Override
	public Advertisement getAdvertisement() {
		return new UserAdvertisement(this);
	}
	
	public UserMessages getMessages() {
		return this.messages;
	}
	
	public void addFriend(String friend) {
		friends.add(friend);
	}
	
	public void deleteFriend(String friend) {
		friends.remove(friend);
	}
	
	public ArrayList<String> getFriends() {
		return friends;
	}
	
	public void publishObjects(DiscoveryService discovery) {
		this.objets.publishObjets(discovery);
	}
	
}
