package model.network.communication.service;

import java.util.HashMap;

import org.jdom2.Element;

import util.StringToElement;
import util.secure.AsymKeysImpl;
import util.secure.encryptionInterface.AsymKeys;
import model.advertisement.AbstractAdvertisement;

/**
 * Create an update message for peers, update message extends AbstractAdvertisment for signature and xml only.
 * @author Julien
 *
 */
public class UpdateMessage extends AbstractAdvertisement{
	
	private String newSignature; //the new signature of the updated object
	private AsymKeys keys; //the keys of updateMessage emitter.
	private String id; //id of object to update
	private String type; //type of update(item, user ..)
	private HashMap<String, String> keysToUpdate; //the others keys to be updated

	
	public UpdateMessage() {
		super();
	}
	
	public UpdateMessage(String xml) {
		super(xml);
	}
	
	public UpdateMessage(Element root) {
		super(root);
	}
	
	
	@Override
	protected String getAdvertisementName() {
		return UpdateMessage.class.getSimpleName();
	}

	@Override
	protected void setKeys() {
		addKey("keysToUpdate", false, false);
		addKey("newSignature", false, false);
		addKey("id", false, false);
		addKey("keys", false, false);
		addKey("type", false, false);
	}

	private String getKeysToUpdateXML() {
		StringBuffer s = new StringBuffer();
		for(String key : keysToUpdate.keySet()) {
			s.append("<" + key + ">" + keysToUpdate.get(key) + "</" + key + ">");
		}
		return s.toString();
	}
	
	@Override
	protected void putValues() {
		addValue("newSignature", newSignature);
		addValue("keys", keys.toString());
		addValue("id", id);
		addValue("type", type);
		addValue("keysToUpdate", getKeysToUpdateXML());
	}

	private void setKeysToUpdate(String xml) {
		Element root = StringToElement.getElementFromString(xml, "update");
		for(Element e : root.getChildren()) {
			keysToUpdate.put(e.getName(), e.getValue());
		}
	}
	
	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "newSignature": newSignature = e.getValue(); return true;
		case "id" : id = e.getValue(); return true;
		case "type"	: type = e.getValue(); return true;
		case "keysToUpdate" : setKeysToUpdate(e.getValue());
		case "keys" : keys = new AsymKeysImpl(e.getValue()); return true;
		default: return false;
		}
	}
}
