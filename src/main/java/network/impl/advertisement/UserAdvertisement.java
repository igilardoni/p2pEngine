package network.impl.advertisement;

import network.api.annotation.AdvertisementAttribute;
import network.impl.AbstractAdvertisement;

public class UserAdvertisement<Sign> extends AbstractAdvertisement<Sign>{

	@AdvertisementAttribute(indexed = true)
	private String nickName;
	
	@AdvertisementAttribute(indexed = true)
	private String publicKey;
	
	@Override
	public String getName() {
		return "user";
	}

	@Override
	public String getAdvertisementType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
