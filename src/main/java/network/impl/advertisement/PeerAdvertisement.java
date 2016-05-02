package network.impl.advertisement;

import network.api.annotation.AdvertisementAttribute;
import network.impl.AbstractAdvertisement;

public class PeerAdvertisement<Sign> extends AbstractAdvertisement<Sign>{
	
	@AdvertisementAttribute
	private String publicKey;
	
	@Override
	public String getName() {
		return "peer";
	}

	@Override
	public String getAdvertisementType() {
		// TODO Auto-generated method stub
		return null;
	}

}
