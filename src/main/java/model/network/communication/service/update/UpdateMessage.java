/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package model.network.communication.service.update;

import java.util.HashMap;

import org.jdom2.Element;

import util.StringToElement;
import util.secure.AsymKeysImpl;
import util.secure.ElGamalSign;
import model.advertisement.AbstractAdvertisement;
import model.data.user.User;

/**
 * Create an update message for peers, update message extends AbstractAdvertisment for signature and xml only.
 * @author Julien
 *
 */
public class UpdateMessage extends AbstractAdvertisement{
	
	private ElGamalSign newSignature; //the new signature of the updated object
	private AsymKeysImpl keys; //the keys of updateMessage emitter.
	private String id; //id of object to update
	private String type; //type of update(item, user ..)
	private Element keysToUpdate = new Element("root");
	
	
	private AbstractAdvertisement adv;
	
	/**
	 * Create the right update message according to the updated object. Compute with the older version of the object.
	 * @param objectUpdated The object that had some change since the last update.
	 * @param emmitterKeys the encryption keys of the object's owner (for exemple current user asym keys.)
	 */
	public UpdateMessage(AbstractAdvertisement updatedObject, AsymKeysImpl emmitterKeys) {
		
		HashMap<String, String> updated = updatedObject.getUpdatableKeys();
		HashMap<String, String> old;
		if(updatedObject.getOld() == null) {
			old = updated;
		} else {
			old = updatedObject.getOld().getUpdatableKeys();
		}
		
		this.newSignature = updatedObject.sign(emmitterKeys);
		this.id = updatedObject.getId();
		this.type = updatedObject.getAdvType();
		this.keys = emmitterKeys;
		
		for(String key : updated.keySet()) {
			if(ignoredKey(key)) continue;
			String newValue = updated.get(key);
			String oldValue = old.get(key);
			if(!newValue.equals(oldValue)) {
				this.addKeyToUpdate(key, newValue);
			}
		}
	}
	
	private boolean ignoredKey(String key) {
		return 
				key.equals("signature") ||
				key.equals("keyId")     ||
				key.equals("lastUpdated");
	}
	
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
		return this.getClass().getSimpleName();
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
		for(Element e : keysToUpdate.getChildren()) {
			s.append("<" + e.getName() + ">" + e.getValue() + "</" + e.getName() + ">");
		}
		return s.toString();
	}
	
	@Override
	protected void putValues() {
		addValue("newSignature", newSignature.toString());
		addValue("keys", keys.toString());
		addValue("id", id);
		addValue("type", type);
		addValue("keysToUpdate", getKeysToUpdateXML());
	}

	private void setKeysToUpdate(String xml) {
		keysToUpdate = StringToElement.getElementFromString(xml, "update");
	}
	
	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "newSignature": newSignature = new ElGamalSign(e.getValue()); return true;
		case "id" : id = e.getValue(); return true;
		case "type"	: type = e.getValue(); return true;
		case "keysToUpdate" : setKeysToUpdate(e.getValue());
		case "keys" : keys = new AsymKeysImpl(e.getValue()); return true;
		default: return false;
		}
	}
	
	public ElGamalSign getNewSignature() {
		return newSignature;
	}
	
	public AsymKeysImpl getKeys() {
		return keys;
	}
	
	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
	public Element getKeysToUpdate() {
		return keysToUpdate;
	}
	
	private void addKeyToUpdate(String key, String value) {
		Element newElem = new Element(key);
		newElem.addContent(value);
		keysToUpdate.addContent(newElem);
	}
	
	/**
	 * Just some input tests
	 * @param args
	 */
	public static void main(String[] args) {
		User u = new User("TestNick", "testPassword", "testName", "testFirstName", "testEmail", "testPhone");
		u.sign(u.getKeys());
		u.throwUpdate(null, u.getKeys());
		u.setName("nameChanged");
		u.setNick("new nick");
		u.throwUpdate(null, u.getKeys());
	}

	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}
	
}
