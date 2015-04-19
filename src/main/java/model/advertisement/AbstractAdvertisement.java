package model.advertisement;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import org.jdom2.Element;
import org.jdom2.IllegalDataException;

import net.jxta.document.Advertisement;
import net.jxta.document.Document;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.TextElement;
import net.jxta.id.ID;

public abstract class AbstractAdvertisement extends Advertisement{

	/*
	 * An hashMap that usually contain the key and value of this advertisement content, 
	 * for generating an XML file for JXTA or for saving datas.
	 */
	protected HashMap<String, String> keyValues = new HashMap<String, String> ();
	
	/*
	 * An array list that contains the indexes for this advertisement. Indexes ar used by JXTA for
	 * advertisements publication and search.
	 */
	protected ArrayList<String> indexes = new ArrayList<String>();
	
	
	
	/**
	 * Instantiate an empty Advertisement, just setting the appropriate keys.
	 */
	public AbstractAdvertisement() {
		super();
		setKeys(); //setting the default keys and indexes for this advertisement.
	}
	
	/**
	 * Instantiate and initialize this advertisement with the given root element.
	 * @param root A root element, that contain all data needed.
	 */
	public AbstractAdvertisement(Element root) {
		this();
		initialize(root);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Instantiate and initialize this advertisement with the given root element given by Jxta.
	 * @param root the root element given by Jxta.
	 */
	public AbstractAdvertisement(net.jxta.document.Element root) {
		this();
		initialize(root);
	}
	
	
	/**
	 * Return the value according to the key. 
	 * @param key The element name.
	 * @return the element value.
	 */
	protected String getValue(String key) {
		if(this.keyValues.containsKey(key)) {
			return this.keyValues.get(key);
		}
		else throw new IllegalArgumentException("Key " + key + " inconnue");
	}
	
	
	/**
	 * Add a new key for this advertisement.
	 * @param key the new key
	 * @param isIndexed true if the key should be indexed for Jxta.
	 */
	protected void addKey(String key, boolean isIndexed) {
		keyValues.put(key, null);
		if(isIndexed) {
			indexes.add(key);
		}
	}
	
	protected void addValue(String key, String value) {
		if(keyValues.get(key) == null) {
			throw new IllegalArgumentException("unknown key");
		}
		keyValues.put(key, value);
	}
	
	
	/**
	 * Return the advertisement name. Used for creating the advertisement type.
	 * For example class.getName()
	 * @return A string that represent the advertisement
	 */
	protected abstract String getAdvertisementName();
	
	@Override
	public String getAdvType() {
		return "jxta:" + getAdvertisementName();
	}
	
	/**
	 * Contruct the root element according to the advertisement name and they key's value.
	 * @return an element.
	 */
	public Element getRootElement() {
		Element root = new Element(getAdvertisementName());
		for(String key: keyValues.keySet()) { //creating an element for each keys. Add to root content.
			Element e = new Element(key);
			e.addContent(keyValues.get(key));
			root.addContent(e);
		}
		return root;
	}
	
	/**
	 * Return a simple document for saving or manipulating the advertisement DOM.
	 * @return a Jdom2 Document.
	 */
	public org.jdom2.Document getDocument() {
		putValues();
		return new org.jdom2.Document(this.getRootElement());
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document getDocument(MimeMediaType mediatype) {
		putValues();
		StructuredDocument adv = StructuredDocumentFactory.newStructuredDocument(mediatype, getAdvType());
		for (String key : keyValues.keySet()) {
			net.jxta.document.Element e = adv.createElement(key,keyValues.get(key));
			adv.appendChild(e);
		}
		return adv;
	}
	
	@Override
	public String[] getIndexFields() {
		return (String[]) indexes.toArray(new String[1]);
	}
	
	/**
	 * Will be called on new instances to set the advertisement's available elements.
	 */
	protected abstract void setKeys();
	
	/**
	 * Will be called to put the class properties in the xml document.
	 */
	protected abstract void putValues();
	
	/**
	 * Initialize this advertisement with the root element handle by JXTA.
	 * @param root
	 */
	@SuppressWarnings("rawtypes")
	protected void initialize(net.jxta.document.Element root) {
		if (!TextElement.class.isInstance(root)) {
            throw new IllegalArgumentException(getClass().getName() + "initialize needs a TextElement.");
        }
		
		TextElement doc = (TextElement) root; //create the doc.
		if (!doc.getName().equals(getAdvType())) {
            throw new IllegalArgumentException(
            		"Error : " + getClass().getName() + " isn't " + doc.getName());
            //Jxta didn't call the right advertisement to construct. Unexpected error.
        }
		
		Enumeration elements = doc.getChildren();
        while(elements.hasMoreElements()) {
        	TextElement elem = (TextElement) elements.nextElement();
        	Element e = new Element(elem.getName()); //convert into a Jdom element.
        	e.addContent(elem.getValue());
        	if (!handleElement(e)) {
                throw new IllegalDataException(elem.getName());
                //this element is unknown for this advertisement.
            }
        }
	}
	
	/**
	 * Initialize this advertisement with a Jdom root element
	 * @param root 
	 */
	protected void initialize(Element root) {
		for(Element e: root.getChildren()) {
			if(!handleElement(e)) {
				throw new IllegalDataException(e.getName());
                //this element is unknown for this advertisement.
			}
		}
	}
	
	/**
	 * Called on each element find in the root element given by Jxta or user.
	 * @param an element
	 * @return true if the element is known and handled. Otherwise false.
	 */
	abstract protected boolean handleElement(Element e);
	
	@Override
	public ID getID() {
		// We don't use id on advertisements.
		return null;
	}

}
