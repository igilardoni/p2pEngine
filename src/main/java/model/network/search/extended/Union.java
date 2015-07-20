package model.network.search.extended;

import model.advertisement.AbstractAdvertisement;

/**
 * Full unions of 2 searchListeners
 * @author Julien Prudhomme
 *
 * @param <T>
 */
public class Union<T extends AbstractAdvertisement> extends BaseExtendedListener<T>{
	
	public Union(ExtendedSearchListener<T> l1, ExtendedSearchListener<T> l2) {
		l1.addListener(this);
		l2.addListener(this);
	}
	
	@Override
	public boolean filter(T event) {
		return true;
	}

}
