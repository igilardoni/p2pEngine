package model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.TextElement;

public abstract class AbstractAdvertisement extends Advertisement{

	private HashMap<String, String> keyval = new HashMap<String, String>();
	private ArrayList<String> indexes = new ArrayList<String>();
	
	public final static Logger LOG = Logger.getLogger(Package.class.getName());
	
	
	public AbstractAdvertisement() {
		super();
		setKeys();
	}
	
	protected abstract void setKeys();
	
	public AbstractAdvertisement(Element root) {
		this();
		TextElement doc = (TextElement) root;
		if(!getAdvertisementType().equals(doc.getName())) {
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
		return (String[]) indexes.toArray();
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
}
