package network.impl.jxta;

import net.jxta.document.Advertisement;
import net.jxta.document.Document;
import net.jxta.document.MimeMediaType;
import net.jxta.id.ID;

public class AdvertisementBridge extends Advertisement{

	private JxtaAdvertisement adv;
	
	public AdvertisementBridge(JxtaAdvertisement adv) {
		this.adv = adv;
	}
	
	@Override
	public Document getDocument(MimeMediaType asMimeType) {
		
		return null;
	}

	@Override
	public ID getID() {
		// TODO voir si on génère un ID pour les adv
		return null;
	}

	@Override
	public String[] getIndexFields() {
		if(adv == null) {
			throw new RuntimeException("Adv is null");
		}
		return adv.getIndexFields();
	}

}
