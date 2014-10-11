package p2pEngine;

import java.io.IOException;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;

/**
 * Représente un objet (offre ou demande)
 * @author Prudhomme Julien
 *
 */

public abstract class Objet implements Advertisable, Comparable<Objet>{

	private String nom;
	
	//voir ObjectAdvertisement
	private String otherName;
	
	private String description;
	private Utilisateur user;
	
	
	/**
	 * Ce constructeur sert à recreer l'objet à partir d'un adv
	 * @param obj
	 */
	public Objet(ObjetAdvertisement obj) {
		setNom(obj.getName());
		setDescription(obj.getDescription());
		setOtherName(obj.getOtherName());
		setUser(new Utilisateur(obj.getNomAuteur(), obj.getTel(), obj.getMail()));
	}
	
	public Objet(String nom, String description, String otherName, Utilisateur user) {
		setNom(nom);
		setDescription(description);
		setOtherName(otherName);
		setUser(user);
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Utilisateur getUser() {
		return user;
	}
	
	public void setUser(Utilisateur user) {
		this.user = user;
	}
	
	/**
	 * Le nom complet de la futur annonce sur le réseau
	 * Typiquement sert a rejouter devant le titre ce que l'on veut
	 * comme Offre:titre ou Demande:titre, ce qui sert pour la recherche
	 * @return String fullName
	 */
	protected abstract String getFullName();
	
	@Override
	public Advertisement getAdvertisement() {
		
		ObjetAdvertisement adv = (ObjetAdvertisement) AdvertisementFactory
				.newAdvertisement(ObjetAdvertisement.getAdvertisementType());
		
		adv.setFullName(getFullName());
		adv.setName(getNom());
		adv.setOtherName(otherName);
		adv.setDescription(description);
		adv.setNomAuteur(user.getNom());
		adv.setTel(user.getTel());
		adv.setMail(user.getMail());
		
		return adv;
	}

	@Override
	public void publish(DiscoveryService discovery) {
		Advertisement adv = getAdvertisement();
		try {
			discovery.publish(adv);
			discovery.remotePublish(adv);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void flush(DiscoveryService discovery) {
		try {
			discovery.flushAdvertisement(getAdvertisement());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	
	public int compareTo(Objet o) {
		// TODO Auto-generated method stub
		
		         
		    //Nous ne prenons pas en compte la casse (majuscules, minuscules...)
		    int i = String.CASE_INSENSITIVE_ORDER.compare(getNom(), o.getNom());
		    if(i != 0) {           
		           return i;
		    }      
		 
		    return String.CASE_INSENSITIVE_ORDER.compare(getOtherName(), o.getOtherName());
		}
}


