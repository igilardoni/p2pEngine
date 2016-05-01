package network.impl.jxta;

import crypt.impl.sigma.ElGamalSign;
import network.impl.AbstractAdvertisement;

public class JxtaAdvertisement extends AbstractAdvertisement<ElGamalSign>{

	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAdvertisementType() {
		return "jxta:" + getName();
	}

}
