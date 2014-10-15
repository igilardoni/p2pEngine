package model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.logging.Logger;

import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.Attributable;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.TextElement;
import net.jxta.id.ID;
import net.jxta.id.IDFactory;

/**
 * Un advertisement est une "annonce" faite sur le réseau
 * celui-ci représente un objet (offre ou demande à spécialiser)
 * @author Prudhomme Julien
 *
 */

public class ObjetAdvertisement extends Advertisement{
	
	private ID id = ID.nullID;
	private final static String idTag = "id";
	
	/*
	 * Pour la recherche : exemple une offre de patate aura comme fullname Offre:Patate
	 * Une demande : Demande:Patate
	 */
	private String fullName;
	private final static String fullNameTag = "fullName";
	
	private String name;
	private final static String nameTag = "name";
	
	/*
	 * Pour une offre, othername correspond à l'objet que l'on veut en échange
	 * Pour une demande, othername correspond à l'objet qu'on peut offrir en échange
	 */
	private String otherName;
	private final static String otherNameTag = "otherName";
	
	private String description;
	private final static String descriptionTag = "description";
	
	private String loginAuteur;
	private final static String loginAuteurTag = "loginAuteur";
	
	private String tel;
	private final static String telTag = "tel";
	
	private String mail;
	private final static String mailTag = "mail";
	
	private final static String[] fields = { idTag, fullNameTag, nameTag, loginAuteurTag };
	public final static Logger LOG = Logger.getLogger(ObjetAdvertisement.class.getName());
	
	
	public ObjetAdvertisement() {
	}
	
	/**
	 * Construit l'annonce à partir d'un TextElement (doc XML)
	 * @param root Le TextElement représentant une instance sous forme XML
	 */
	public ObjetAdvertisement(Element root) {
		TextElement doc = (TextElement) root;
		if(!getAdvertisementType().equals(doc.getName())) {
			throw new IllegalArgumentException(
                    "Pour construire : " + getClass().getName() + " nécessite " + doc.getName());
		}
		
		//Ne pas oublier biensur, pour pas se creuser la tete et se
		//demander pourquoi on recoit des adv vide pendant 2h !!!!!!!!
		initialize(root);
		
	}
	
	protected void initialize(Element root) {
		if (!TextElement.class.isInstance(root)) {
            throw new IllegalArgumentException(getClass().getName() + " nécessite un TextElement");
        }
		TextElement doc = (TextElement) root;
		if (!doc.getName().equals(getAdvertisementType())) {
            throw new IllegalArgumentException(
            		"Pour construire : " + getClass().getName() + " nécessite " + doc.getName());
        }
		
        Enumeration elements = doc.getChildren();

        while (elements.hasMoreElements()) {
            TextElement elem = (TextElement) elements.nextElement();

            if (!handleElement(elem)) {
                LOG.warning("element inconnu \'" + elem.getName() + "\' dans " + doc.getName());
            }
        }
	}
	
	/**
	 * Essaye de récuperer la valeur d'une balise connue
	 * @param elem Un Textelement
	 * @return true si l'élément a été reconnue et assigné
	 */
	protected boolean handleElement(TextElement elem) {
		
		//on récupére l'id et on vérifie que celui-ci a un format correct
		 if (elem.getName().equals(idTag)) {
	            try {
	                URI id = new URI(elem.getTextValue());

	                setID(IDFactory.fromURI(id));
	            } catch (URISyntaxException badID) {
	                throw new IllegalArgumentException("Format de l'id incconu : " + elem.getTextValue());
	            } catch (ClassCastException badID) {
	                throw new IllegalArgumentException("Type inconnu: " + elem.getTextValue());
	            }
	            return true;
		 }
		 
		 //on récupére le nom de l'annonce
		 if(elem.getName().equals(nameTag)) {
			 setName(elem.getTextValue());
			 return true;
		 }
		 
		 if(elem.getName().equals(descriptionTag)) {
			 setDescription(elem.getTextValue());
			 return true;
		 }
		 
		 if(elem.getName().equals(loginAuteurTag)) {
			 setLoginAuteur(elem.getTextValue());
			 return true;
		 }
		 
		 if(elem.getName().equals(telTag)) {
			 setTel(elem.getTextValue());
			 return true;
		 }
		 
		 if(elem.getName().equals(mailTag)) {
			 setMail(elem.getTextValue());
			 return true;
		 }
		 
		 if(elem.getName().equals(fullNameTag)) {
			 setFullName(elem.getTextValue());
			 return true;
		 }
		 
		 if(elem.getName().equals(otherNameTag)) {
			 setOtherName(elem.getTextValue());
			 return true;
		 }
		 
		 //element non reconnu
		 return false;
		 
	}
	
	@Override
	public Document getDocument(MimeMediaType asMimeType) {
		
		 StructuredDocument adv = StructuredDocumentFactory.newStructuredDocument(asMimeType, getAdvertisementType());
		
		//on verifie qu'on peut ajouter des attributs
		if (adv instanceof Attributable) {
            ((Attributable) adv).addAttribute("xmlns:jxta", "http://jxta.org");
        }
        Element e;
        
        e = adv.createElement(idTag, getID().toString());
        adv.appendChild(e);
        
        e = adv.createElement(fullNameTag, getFullName());
        adv.appendChild(e);
        
        e = adv.createElement(nameTag, getName());
        adv.appendChild(e);
        
        e = adv.createElement(otherNameTag, getOtherName());
        adv.appendChild(e);
        
        e = adv.createElement(descriptionTag, getDescription());
        adv.appendChild(e);
        
        e = adv.createElement(loginAuteurTag, getLoginAuteur());
        adv.appendChild(e);
        
        e = adv.createElement(telTag, getTel());
        adv.appendChild(e);
        
        e = adv.createElement(mailTag, getMail());
        adv.appendChild(e);
		
        //notre annonce est construite !
		return adv;
	}
	
    /**
     * Défini le type de l'annonce
     * @return jxta:Le nom de la classe
     */
    public static String getAdvertisementType() {
        return "jxta:" + ObjetAdvertisement.class.getName();
    }
    
    /**
     * Instanciator de la classe ObjectAdvertisement
     * Tout les advertisements doivent être instancier avec la factory
     * C'est comme ça !
     * @author Prudhomme Julien
     *
     */
    public static class Instantiator implements AdvertisementFactory.Instantiator {

        /**
         * Retourne l'identifiant de l'adv
         *
         * @return String le type de l'adv
         */
        public String getAdvertisementType() {
            return ObjetAdvertisement.getAdvertisementType();
        }

        /**
         * Construit une instance d'Advertisement <CODE>Advertisement</CODE> qui
         * correspond au type fourni par le paramètre <CODE>advertisementType</CODE>
         *
         * @return L'instance de l' <CODE>Advertisement</CODE> ou null si
         * la création a échouée
         */
        public Advertisement newInstance() {
            return new ObjetAdvertisement();
        }

        /**
         * Construit une instance d'Advertisement <CODE>Advertisement</CODE> qui
         * correspond au type fourni par le param�tre <CODE>advertisementType</CODE>
         *
         * @param root La racine d'un StructuredElement qui sera converti en Advertisement
         * @return L'instance de l' <CODE>Advertisement</CODE> ou null si
         * la création a échouée
         */
        public Advertisement newInstance(Element root) {
            return new ObjetAdvertisement(root);
        }
    }
    
    /**
     * Enregistre la classe dans la Factory des Advertisements
     */
    public static void register() {
    	AdvertisementFactory.registerAdvertisementInstance(ObjetAdvertisement.getAdvertisementType()
                ,
                new ObjetAdvertisement.Instantiator());
    }

	@Override
	public ID getID() {
		return id == null ? null:id;
	}

	@Override
	public String[] getIndexFields() {
		return fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLoginAuteur() {
		return loginAuteur;
	}

	public void setLoginAuteur(String nomAuteur) {
		this.loginAuteur = nomAuteur;
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

	public void setID(ID id) {
		this.id = id == null ? null:id;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
