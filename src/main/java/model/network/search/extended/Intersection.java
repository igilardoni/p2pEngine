package model.network.search.extended;

import java.util.HashMap;

import model.advertisement.AbstractAdvertisement;

/**
 * Intersection of 2 ExtendedSearchListener instances.
 * @author Julien Prudhomme
 *
 * @param <T>
 */
public class Intersection<T extends AbstractAdvertisement> extends BaseExtendedListener<T>{

	private HashMap<String, T> tmp = new HashMap<>();
	
	public Intersection(ExtendedSearchListener<T> l1, ExtendedSearchListener<T> l2) {
		l1.addListener(this);
		l2.addListener(this);
	}
	
	@Override
	public boolean filter(T event) {
		if(tmp.containsKey(event.getId())) {
			return true;
		}
		else {
			tmp.put(event.getId(), event);
			return false;
		}
	}

}
