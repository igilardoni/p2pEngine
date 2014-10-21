package model;

import java.util.HashMap;
import java.util.Map.Entry;

import net.jxta.document.Advertisement;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.TextElement;
import net.jxta.id.ID;

public class AbstractAdvertisement extends Advertisement{

	private HashMap<String, String> keyval;
	
	
	public AbstractAdvertisement(Element root) {
		TextElement doc = (TextElement) root;
		if(!getAdvertisementType().equals(doc.getName())) {
			throw new IllegalArgumentException(
                    "Pour construire : " + getClass().getName() + " nécessite " + doc.getName());
		}
		// TODO
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
		return DocumentGenerator.getGeneratedDocument(asMimeType, getAdvertisementType(), keyset);
	}
	

	@Override
	public ID getID() {
		// on a pas besoin d'id propre pour l'instant
		return ID.nullID;
	}

	@Override
	public String[] getIndexFields() {
		// TODO Auto-generated method stub
		return null;
	}

}
