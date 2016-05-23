package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import net.jxta.document.Advertisement;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.TextElement;

/**
 * Repr�sente un Advertisement. Les advertisement sont utiles pour envoyer ou recevoir des donn�es sur le r�seau.
 * @author Prudhomme Julien
 *
 * @param <T> un objet Advertisable
 */

public abstract class AbstractAdvertisement<T extends Advertisable> extends Advertisement implements Serializable{

	private static final long serialVersionUID = 6800858564387966533L;
	private HashMap<String, String> keyval = new HashMap<String, String>();
	private ArrayList<String> indexes = new ArrayList<String>();
	
	public final static Logger LOG = Logger.getLogger(Package.class.getName());
	
	
	/**
	 * Creer l'advertisement
	 */
	public AbstractAdvertisement() {
		super();
		setKeys();
	}
	
	/**
	 * Permet de definir les cl�s avec addKey()
	 */
	
	protected abstract void setKeys();
	
	
	/**
	 * Retourne la valeur d'une cl� si elle est d�finie.
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		if(this.keyval.containsKey(key)) {
			return this.keyval.get(key);
		}
		else throw new IllegalArgumentException("Key " + key + " inconnue");
	}
	
	/**
	 * Cr�er un advertisement � partir d'un document (xml)
	 * @param root La racine d'un document, xml par exemple.
	 */
	
	public AbstractAdvertisement(Element root) {
		this();
		TextElement doc = (TextElement) root;
		if(!getAdvType().equals(doc.getName())) {
			throw new IllegalArgumentException(
                    "Pour construire : " + getClass().getName() + " nécessite " + doc.getName());
		}
		initialize(doc);
	}
	
	protected void initialize(Element root) {
		if (!TextElement.class.isInstance(root)) {
            throw new IllegalArgumentException(getClass().getName() + " nécessite un TextElement");
        }
		TextElement doc = (TextElement) root;
		if (!doc.getName().equals(getAdvType())) {
            throw new IllegalArgumentException(
            		"Pour construire : " + getClass().getName() + " nécessite " + doc.getName());
        }
		
        Enumeration elements = doc.getChildren();
        while(elements.hasMoreElements()) {
        	TextElement elem = (TextElement) elements.nextElement();
        	if (!handleElement(elem)) {
                LOG.warning("element inconnu \'" + elem.getName() + "\' dans " + doc.getName());
            }
        }
	}
	
	
	/**
	 * V�rifie si l'�lement trouv� correspond � une cl�, si oui enregistre sa valeur dans la cl� correspondante
	 * @param elem
	 * @return
	 */
	protected boolean handleElement(TextElement elem) {
		if(keyval.containsKey(elem.getName())) {
			keyval.put(elem.getName(), elem.getValue());
			return true;
		}
		return false;
	}
	
	@Override
	public Document getDocument(MimeMediaType asMimeType) {
		String[] keyset = new String[keyval.size()*2];
		int i = 0;
		for(Entry<String, String> entry: keyval.entrySet()) {
			keyset[i] = entry.getKey();
			keyset[i+1] = entry.getValue();
			i+=2;
		}
		return DocumentGenerator.getGeneratedDocument(asMimeType, getAdvType(), keyset);
	}

	public abstract String getAdvType();
	
	@Override
	public String[] getIndexFields() {
		return (String[]) indexes.toArray(new String[1]);
	}
	
	protected void addKey(String key, boolean isIndexed) {
		keyval.put(key, null);
		if(isIndexed) {
			indexes.add(key);
		}
	}
	
	protected void putValue(String key, String value) {
		if(keyval.containsKey(key)) {
			keyval.put(key, value);
		}
		else throw new IllegalArgumentException("Key " + key + " inconnue");
	}
	
	public abstract T toClass();
}
