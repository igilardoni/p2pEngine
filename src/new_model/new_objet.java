package new_model;

import java.util.Date;

/**
 * Class new_objet : description of objects
 * @author CHTIWI El Mehdi
 *
 */

public class new_objet {
	
	private new_User proprietaire;
	private  String titre;
	private  Categorie categorie;
	private String description;
	private String image;
	private Zone zone;
	private String contact;
	private long date;
	private long vie;
	private boolean proposition;
	private boolean souhaite;
	
	public new_objet(new_User proprietaire,String titre,
			Categorie categorie, String description, String image,
			Zone zone,String contact,long date,long vie,boolean proposition,
			boolean souhaite){
		
		this.proprietaire = proprietaire;
		this.titre = titre;
		this.categorie = categorie;
		this.description = description ;
		this.image = image;
		this.zone = zone;
		this.contact = contact;
		this.date = date;
		this.vie = vie;
		this.proposition = proposition;
		this.souhaite = souhaite;
	}
	
	public new_objet(){
		
	}

	public new_User getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(new_User proprietaire) {
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

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
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

	public boolean isProposition() {
		return proposition;
	}

	public void setProposition(boolean proposition) {
		this.proposition = proposition;
	}

	public boolean isSouhaite() {
		return souhaite;
	}

	public void setSouhaite(boolean souhaite) {
		this.souhaite = souhaite;
	}
	
	

}
