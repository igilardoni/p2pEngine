package model.objet;

import java.util.Locale;

import model.user.User;

/* TODO tout mettre en anglais svp ! 
 * Pense a commenter ton code (quelqu'un d'exterieur peut mettre 5 min 
 * a regarder ailleur avant de comprendre a quoi correspond tel ou tel attribute.)
 * Locale zone sert a quoi ?
 * 
 * */


/**
 * Class Objet : description of objects
 */

public class Objet {
	
	public enum TYPE{
		SOUHAIT,
		PROPOSITION
	};
	
	private User proprietaire;
	private String titre;
	private Categorie categorie;
	private String description;
	private String image;
	private Locale zone;
	private String contact;
	private long date;
	private long vie;
	private TYPE type;
	
	public Objet(User proprietaire,String titre,
			Categorie categorie, String description, String image,
			Locale zone,String contact,long date,long vie,TYPE type){
		
		this.proprietaire = proprietaire;
		this.titre = titre;
		this.categorie = categorie;
		this.description = description ;
		this.image = image;
		this.zone = zone;
		this.contact = contact;
		this.date = date;
		this.vie = vie;
		this.setType(type);
	}
	
	public Objet(){
		
	}

	public User getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(User proprietaire) {
		this.proprietaire = proprietaire;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Locale getZone() {
		return zone;
	}

	public void setCountry(String language, String pays){
		zone = new Locale(language, pays);
	}
	
	public String getCountry(){
		return zone.getDisplayCountry();
	}
	
	public String getLanguage(){
		return zone.getLanguage();
	}

	public void setZone(Locale zone) {
		this.zone = zone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getVie() {
		return vie;
	}

	public void setVie(long vie) {
		this.vie = vie;
	}
	
	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
	

}
