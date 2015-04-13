package new_model;

import java.math.BigInteger;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class new_user {
	private String login;
	private String password;
	private String nom;
	private String prenom;
	private String email;
	private String tel;
	private BigInteger cle_prive;
	private BigInteger cle_public;
	private String id_peer;
	//private ArrayList<Note> list_notes;
	
	public new_user(String login,String password,String nom,
			String prenom,String email,
			String tel,BigInteger cle_prive, BigInteger cle_public,String id_peer
			){
		
		this.login = login;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom; 
		this.email = email;
		this.tel = tel;
		this.cle_prive = cle_prive;
		this.cle_public = cle_public;
		this.id_peer = id_peer;
		//this.list_notes =  new ArrayList<Note>();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public BigInteger getCle_prive() {
		return cle_prive;
	}

	public void setCle_prive(BigInteger cle_prive) {
		this.cle_prive = cle_prive;
	}

	public BigInteger getCle_public() {
		return cle_public;
	}

	public void setCle_public(BigInteger cle_public) {
		this.cle_public = cle_public;
	}

	public String getId_peer() {
		return id_peer;
	}

	public void setId_peer(String id_peer) {
		this.id_peer = id_peer;
	}

	
	
	

}
