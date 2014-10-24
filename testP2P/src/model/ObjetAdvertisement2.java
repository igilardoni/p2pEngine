package model;

import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.id.ID;

public class ObjetAdvertisement2 extends AbstractAdvertisement{

	
	public ObjetAdvertisement2(Objet obj) {
		super();
		putValue("titre", obj.getTitre());
		putValue("resume", obj.getResume());
		putValue("desc", obj.getDesc());
		putValue("img", obj.getImg());
		putValue("proposition", Boolean.toString(obj.isProposition()));
		putValue("souhait", Boolean.toString(obj.isSouhait()));
		putValue("troc", Boolean.toString(obj.isTroc()));
		putValue("vente", Boolean.toString(obj.isVente()));
		putValue("date", Long.toString(obj.getDate()));
	}

	@Override
	protected void setKeys() {
		this.addKey("titre", true);
		this.addKey("resume", false);
		this.addKey("desc", false);
		this.addKey("img", false);
		this.addKey("date", false);
		this.addKey("proposition", false);
		this.addKey("souhait", false);
		this.addKey("troc", false);
		this.addKey("vente", false);
		
		// TODO
	}

	@Override
	public ID getID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String getAdvertisementType() {
		return "jxta:" + ObjetAdvertisement2.class.getName();
	}
	
	public static void register() {
		AdvertisementFactory.registerAdvertisementInstance(ObjetAdvertisement.getAdvertisementType(),
                										   new AdvertisementInstaciator(ObjetAdvertisement2.class, getAdvertisementType()));
	}
	
	public static void main(String[] args) {
		Objet obj = new Objet(true, false, true, false, "des patates", "des patates bien fraiches", "<h1>la description</h1>", null, null);
		obj.setDate(System.currentTimeMillis());
		AbstractAdvertisement adv = new ObjetAdvertisement2(obj);
		System.out.println(obj.getDate());
		System.out.println(adv.getDocument(MimeMediaType.XMLUTF8));
	}

	@Override
	public String getAdvType() {
		return getAdvertisementType();
	}

}
