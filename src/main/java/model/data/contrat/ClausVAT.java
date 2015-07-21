package model.data.contrat;

import org.jdom2.Element;

/**
 * TODO ALL
 * @author Michael Dubuis
 *
 */
public class ClausVAT extends Claus {
	public ClausVAT(Element e) {
	}
	
	@Override
	protected String getAdvertisementName() {
		return null;
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
