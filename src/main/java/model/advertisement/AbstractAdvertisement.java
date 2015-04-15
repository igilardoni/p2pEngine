package model.advertisement;

import java.util.ArrayList;
import java.util.HashMap;

import net.jxta.document.Advertisement;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.TextElement;
import net.jxta.document.XMLElement;

public abstract class AbstractAdvertisement<T> extends Advertisement implements AbstractAdvertisementInterface {
	protected HashMap<String, String> keyValue = new HashMap<String, String>();
	protected ArrayList<String> indexes = new ArrayList<String>();
	
	public AbstractAdvertisement(){
		super();
		setKeys();
	}
	
	/**
	 * Used for extract a value of a known key
	 * @return this.keyValue.get(key)
	 * @throws IllegalArgumentException if the key is unknown
	 */
	public String getValue(String key) {
		if(this.keyValue.containsKey(key)) {
			return this.keyValue.get(key);
		}
		else throw new IllegalArgumentException("Key " + key + " inconnue");
	}
	
	/**
	 * Used to add a key
	 * @param key
	 * @param isIndexed true if the key is indexed, false else.
	 */
	protected void addKey(String key, boolean isIndexed) {
		keyValue.put(key, null);
		if(isIndexed) {
			indexes.add(key);
		}
	}
	
	/**
	 * Used to define value of a known key
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException if the key is unknown
	 */
	protected void putValue(String key, String value){
		if(keyValue.containsKey(key))
			keyValue.put(key, value);
		else throw new IllegalArgumentException("Unknown key : " + key);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public XMLElement rootElement(){
		XMLElement root = null;
		for (String key : keyValue.keySet()) {
			root.appendChild(getElement(key));
		}
		return root;
	}
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public Document getDocument(MimeMediaType arg0){
		StructuredDocument adv = StructuredDocumentFactory.newStructuredDocument(arg0, getAdvType());
		Element e;
		for (String key : keyValue.keySet()) {
			if(keyValue.get(key) != "" && keyValue.get(key) !=null){
				e = adv.createElement(key,keyValue.get(key));
				adv.appendChild(e);
			}
		}
		return adv;
	}
	
	/////////////// ABSTRACT METHODS \\\\\\\\\\\\\\\
	/**
	 * Used to define Keys
	 */
	protected abstract void setKeys();
	/**
	 * Used to create Element for a known key
	 */
	@SuppressWarnings("rawtypes")
	protected abstract Element getElement(String key);
	/**
	 * Used to add all keys
	 */
	protected abstract void putKeys();
}
