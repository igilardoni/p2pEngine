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
