/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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
