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
package model.network.search;

import java.util.ArrayList;

import util.secure.AsymKeysImpl;
import model.advertisement.AbstractAdvertisement;

/**
 * A simple arrayList that don't accept doublon or unsigned object, and replace an entry by a new one if the date is more recent
 * @author Julien Prudhomme
 *
 * @param <T>
 */
public class SearchList<T extends AbstractAdvertisement> {
	private ArrayList<T> list;
	
	public SearchList() {
		list = new ArrayList<T>();
	}
	
	
	/**
	 * Add a new object in the list. The object is added if :
	 * It is not in the list.
	 * It's correctly signed.
	 * It's already in the list but the new one is more recent. The old is deleted.
	 * @param obj the object to add
	 * @return this obj if it's added or null
	 */
	public T add(T obj, AsymKeysImpl keys) {
		if(!obj.checkSignature(keys)) return null;
		int oldIndex = list.indexOf(obj);
		if(oldIndex != -1) {
			T old = list.get(oldIndex);
			if(old.getLastUpdated() < obj.getLastUpdated()) {
				list.add(oldIndex, obj);
				list.remove(oldIndex + 1);
				return obj;
			}
		}
		
		list.add(obj);
		
		return obj;
	}
	
	
}
