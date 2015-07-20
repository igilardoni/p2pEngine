package model.network.search.extended;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import model.advertisement.AbstractAdvertisement;

public abstract class BaseExtendedListener<T extends AbstractAdvertisement> implements ExtendedSearchListener<T>{

	protected ArrayList<ExtendedSearchListener<T>> listeners = new ArrayList<>();
	protected HashMap<String, T> results = new HashMap<>(); //KeyId, AbstractAdvertisement
	
	@Override
	public void searchEvent(T event) {
		if(addResult(event)) notifyListeners(event);
	}
	
	/**
	 * Filter the result event
	 * @param event
	 * @return true if the event is accepted, otherwise false
	 */
	public abstract boolean filter(T event);
	
	/**
	 * Add a new result
	 * @param result
	 * @return true if the result was added, otherwise false (result already exist and more recent, or filtered)
	 */
	public boolean addResult(T result) {
		if(!filter(result)) return false;
		T adv = results.get(result.getId());
		if(adv == null) {
			results.put(result.getId(), result);
			return true;
		}
		else {
			if(result.getLastUpdated() > adv.getLastUpdated()) {
				results.put(result.getId(), result);
				return true;
			}
			else return false;
		}
		
		
	}

	@Override
	public void addListener(ExtendedSearchListener<T> l) {
		listeners.add(l);
	}

	@Override
	public void notifyListeners(T adv) {
		for(ExtendedSearchListener<T>  l : listeners) {
			l.searchEvent(adv);
		}
	}

	@Override
	public Collection<T> getResults() {
		return results.values();
	}

}
