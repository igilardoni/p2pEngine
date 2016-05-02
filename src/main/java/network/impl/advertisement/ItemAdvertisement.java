package network.impl.advertisement;

import network.api.annotation.AdvertisementAttribute;
import network.impl.AbstractAdvertisement;

/**
 * Advertisement for an Item
 * @author Julien Prudhomme
 *
 * @param <Sign>
 */
public class ItemAdvertisement<Sign> extends AbstractAdvertisement<Sign> {

	@AdvertisementAttribute(indexed = true)
	private String title;
	
	@AdvertisementAttribute(indexed = true)
	private String authorNickname;
	
	@Override
	public String getName() {
		return "item";
	}

	@Override
	public String getAdvertisementType() {
		return null;
	}

}
