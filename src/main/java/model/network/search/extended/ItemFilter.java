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

import model.data.item.Item;

/**
 * Filter items
 * @author Julien Prudhomme
 *
 */
public class ItemFilter extends BaseExtendedListener<Item>{

	private String user = null;
	private String title = null;
	private String country = null;
	private Item.TYPE type = null;
	
	/**
	 * Create a new Item filter instance
	 * Let an arg null -> no filter on this arg
	 * @param user will keep item only if user is equal
	 * @param title will keep item only if title is equal
	 * @param country
	 * @param type
	 */
	public ItemFilter(String user, String title, String country, Item.TYPE type) {
		this.user = user;
		this.title = title;
		this.country = country;
		this.type = type;
	}
	
	@Override
	public boolean filter(Item event) {
		if(!(user != null && event.getFriendNick().equals(user))) {
			return false;
		}
			
		if(!(title != null && event.getTitle().equals(title))) {
			return false;
		}
		
		if(!(country != null && event.getCountry().equals(country))) {
			return false;
		}
		
		if(!(type != null && event.getType().equals(type))) {
			return false;
		}
		return true;
	}

	

}
