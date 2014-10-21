package model;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;

/**
 * Représente un objet (offre ou demande)
 * @author Prudhomme Julien
 *
 */

public class Objet implements Advertisable, Comparable<Objet>, Serializable{

	private static final long serialVersionUID = -655234892052824494L;
	
	private String titre;
	private String resume;
	private String desc;
	private String img;
	private long date;
	private boolean proposition, souhait;
	private boolean troc, vente;
	
	//voir ObjectAdvertisement
	private String otherName;
	
	private String description;
	private User user;
	
	
	/**
	 * Ce constructeur sert à recreer l'objet à partir d'un adv
	 * @param obj
	 */
	public Objet(ObjetAdvertisement obj) {
	//	setNom(obj.getName());
	//	setDescription(obj.getDescription());
	//	setOtherName(obj.getOtherName());
		//TODO setUser(new User(null, null, obj.getTel(), obj.getMail(), obj.getLoginAuteur(), null));
	}
	
	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean isProposition() {
		return proposition;
	}

	public void setProposition(boolean proposition) {
		this.proposition = proposition;
	}

	public boolean isSouhait() {
		return souhait;
	}

	public void setSouhait(boolean souhait) {
		this.souhait = souhait;
	}

	public boolean isTroc() {
		return troc;
	}

	public void setTroc(boolean troc) {
		this.troc = troc;
	}

	public boolean isVente() {
		return vente;
	}

	public void setVente(boolean vente) {
		this.vente = vente;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Objet(boolean proposition, boolean souhait, boolean troc, boolean vente, 
			String titre, String resume, String desc, String img, User user) {
		this.proposition = proposition;
		this.souhait = souhait;
		this.troc = troc;
		this.vente = vente;
		this.titre = titre;
		this.resume = resume;
		this.desc = desc;
		this.img = img;
		this.user = user;
	}
	
	public String getTitre() {
		return titre;
	}
	
	

	
	@Override
	public Advertisement getAdvertisement() {
		
		ObjetAdvertisement adv = (ObjetAdvertisement) AdvertisementFactory
				.newAdvertisement(ObjetAdvertisement.getAdvertisementType());
		
		// TODO adv.setFullName(getFullName());
		// TODO adv.setName(getNom());
		adv.setOtherName(otherName);
		adv.setDescription(description);
		adv.setLoginAuteur(user.getNom());
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

	public int compareTo(Objet o) {
		    //Nous ne prenons pas en compte la casse (majuscules, minuscules...)
		   return String.CASE_INSENSITIVE_ORDER.compare(getTitre(), o.getTitre());
		}
	
	public long getDate() {
		return this.date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public String getFormatedDate(String style) {
		SimpleDateFormat format = new SimpleDateFormat(style);
		Date d = new Date(getDate());
		return format.format(d);
	}
	
	public String getSimpleDate() {
		return getFormatedDate("dd/mm/yy");
	}
	
	public String getSimpleTime() {
		return getFormatedDate("HH:mm:ss");
	}
}


