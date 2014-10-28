package model;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.DocumentException;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;

/**
 * Représente un objet (offre ou demande)
 * @author Prudhomme Julien
 *
 */

public class Objet extends AbstractAdvertisable implements Comparable<Objet>, Serializable{

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
	private User user;
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTitre() {
		return titre;
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


	public ObjetPdfModel createModel(){
		return new ObjetPdfModel(this);
	}
	
	public void createPDF(){
		ObjetPdfModel model = createModel();
		try {
			new PDFGenerator(model);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	

	
	@Override
	public Advertisement getAdvertisement() {
		return new ObjetAdvertisement(this);
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
		return getFormatedDate("dd/MM/yy");
	}
	
	public String getSimpleTime() {
		return getFormatedDate("HH:mm:ss");
	}

	@Override
	public void update(DiscoveryService discovery) {
		flush(discovery);
		publish(discovery);
	}
}

