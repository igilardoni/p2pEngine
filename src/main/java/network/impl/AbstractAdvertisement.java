package network.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;

import network.api.Advertisement;
import network.api.Peer;
import network.api.annotation.AdvertisementAttribute;
import network.api.annotation.ServiceName;

public abstract class AbstractAdvertisement<Sign> implements Advertisement<Sign>{

	@AdvertisementAttribute(signable = false)
	protected Sign sign;
	
	@Override
	public void setSign(Sign s) {
		this.sign = s;
	}

	@Override
	public Sign getSign() {
		return sign;
	}

	@Override
	public byte[] getHashableData() {
		ArrayList<String> data = new ArrayList<>();
		for(Field field: this.getClass().getDeclaredFields()) {
			AdvertisementAttribute a = field.getAnnotation(AdvertisementAttribute.class);
			if(a != null && a.enabled() && a.signable()) {
				try {
					data.add((String) field.get(this));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		java.util.Collections.sort(data);
		ArrayList<byte[]> bytesList = new ArrayList<>();
		int total = 0;
		for(String s : data) {
			bytesList.add(s.getBytes());
			total += s.getBytes().length;
		}
		byte[] res = new byte[total];
		
		int i = 0;
		for(byte[] b : bytesList) {
			System.arraycopy(b, 0, res, i, b.length);
			i += b.length;
		}
		return res;
	}

	@Override
	public abstract String getName();

	@Override
	public abstract String getAdvertisementType();

	@Override
	public void publish(Peer peer) {
		ServiceName name = this.getClass().getAnnotation(ServiceName.class);
		if(name == null) {
			throw new RuntimeException("No service name defined for this class");
		}
		peer.getService(name.name()).publishAdvertisement(this);
	}
	
	@Override
	public Document getDocument() {
		Element root = new Element(getName());
		for(Field field : this.getClass().getFields()) {
			AdvertisementAttribute a = field.getAnnotation(AdvertisementAttribute.class);
			if(a != null && a.enabled()) {
				Element e = new Element(field.getName());
				try {
					e.addContent((String) field.get(this));
					root.addContent(e);
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}
		return new Document(root);
	}
	
	@Override
	public void initialize(Document doc) {
		Element root = doc.getRootElement();
		for(Element e : root.getChildren()) {
			try {
				Field f = this.getClass().getField(e.getName());
				f.set(this, e.getValue());
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
	}

}
