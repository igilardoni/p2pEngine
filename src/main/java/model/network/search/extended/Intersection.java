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
