package network.impl.jxta;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

import net.jxta.document.MimeMediaType;
import network.api.Advertisement;
import network.api.Peer;
import protocol.impl.sigma.ElGamalSign;

public class JxtaAdvertisement implements Advertisement<ElGamalSign>{

	private Advertisement<ElGamalSign> adv;
	
	
	public JxtaAdvertisement(Advertisement<ElGamalSign> adv) {
		System.out.println("Adv : \n" + new XMLOutputter().outputString(adv.getDocument()));
		this.adv = adv;
	}
	
	@Override
	public String getName() {
		return adv.getName();
	}

	@Override
	public String getAdvertisementType() {
		return "jxta:" + getName();
	}
	
	public AdvertisementBridge getJxtaAdvertisementBridge() {
		System.out.println(new AdvertisementBridge(this).getDocument(MimeMediaType.XML_DEFAULTENCODING));
		return new AdvertisementBridge(this);
	}

	@Override
	public void setSign(ElGamalSign s) {
		adv.setSign(s);
	}

	@Override
	public ElGamalSign getSign() {
		return adv.getSign();
	}

	@Override
	public byte[] getHashableData() {
		return adv.getHashableData();
	}

	@Override
	public void publish(Peer peer) {
		adv.publish(peer);
	}

	@Override
	public void initialize(Document doc) {
		adv.initialize(doc);
	}

	@Override
	public Document getDocument() {
		return adv.getDocument();
	}

	@Override
	public String[] getIndexFields() {
		return adv.getIndexFields();
	}

	@Override
	public String getSourceURI() {
		return adv.getSourceURI();
	}

	@Override
	public void setSourceURI(String uri) {
		adv.setSourceURI(uri);
	}

}
