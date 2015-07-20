package model.network.search.extended;

import java.util.Collection;

import model.advertisement.AbstractAdvertisement;


/**
 * Interface for extended search. Possibility to link several ExtendedSearchListener (union, intersection, ...)
 * @author Julien Prudhomme
 *
 * @param <T> Abstract Advertisement type
 */
public interface ExtendedSearchListener <T extends AbstractAdvertisement>{

	/**
	 * Search event triggered (by JXTA or an other ExtendedSearchListener)
	 * @param event
	 */
	public void searchEvent(T event);
	
	/**
	 * Add an other listener to this instance
	 * @param l
	 */
	public void addListener(ExtendedSearchListener<T> l);
	
	/**
	 * Notify all the listener that an event had occured.
	 * @param adv
	 */
	public void notifyListeners(T adv);
	
	/**
	 * Get all the current results received.
	 * @return a Collection of results
	 */
	public Collection<T> getResults();
	
	
}
