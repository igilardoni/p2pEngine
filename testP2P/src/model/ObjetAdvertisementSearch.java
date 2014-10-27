package model;


public class ObjetAdvertisementSearch extends AdvertisementSearch<ObjetAdvertisement>{

	public ObjetAdvertisementSearch(Peer peer) {
		super(peer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void search(String key, String value) {
		super.search(key, "*"+value+"*");
	}
	
	@Override
	protected void action() {
		// TODO Auto-generated method stub
		
	}

}
