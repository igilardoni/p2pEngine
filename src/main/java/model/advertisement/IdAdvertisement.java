package model.advertisement;

import org.jdom2.Element;

/**
 * Short representing of an advertisement (just Id & publicKey) to save network data
 * @author Julien Prudhomme
 * TODO IdAdvertisement not finished ?
 */
public class IdAdvertisement extends AbstractAdvertisement{

	
	public IdAdvertisement(AbstractAdvertisement adv) {
		super();
		this.setId(adv.getId());
		this.setKeys(adv.getKeys());
	}
	
	@Override
	protected String getAdvertisementName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void setKeys() {
	}

	@Override
	protected void putValues() {
	}

	@Override
	protected boolean handleElement(Element e) {
		return false;
	}

	@Override
	public String getSimpleName() {
		return null;
	}

}
