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

import model.Application;
import model.data.item.Item;
import model.data.user.User;
import model.network.NetworkInterface;
import util.VARIABLES;

/**
 * This class is used for search an unique item
 * @author Michael Dubuis
 *
 */
public class ItemSearcher {
	private NetworkInterface network;
	
	/**
	 * Constructor with network parameter
	 * @param network
	 */
	public ItemSearcher(NetworkInterface network){
		this.network = network;
	}
	
	/**
	 * Search an item with his unique key
	 * @param itemKey
	 * @return unique item if exist on network, well signed. Else return null.
	 */
	public Item search(String itemKey){
		ArrayList<Item> itemList;
		
		Search<Item> itemSearch = new Search<Item>(Application.getInstance().getNetwork(), Item.class.getSimpleName(), "keyId", true);
		itemSearch.search(itemKey, VARIABLES.MaxTimeSearch, VARIABLES.ReplicationsAccount);
		itemList = itemSearch.getResults();
		long maxDateItem = 0;
		for(Item i : itemList){
			if(!i.checkSignature(i.getKeys())){
				itemList.remove(i); // Removes items that are fallacious
				continue;
			}
			if(maxDateItem >= i.getLastUpdated()){
				itemList.remove(i); // Removes items that are less up to date (or as much)
				continue;
			}
			maxDateItem = i.getLastUpdated();
		}
		for(Item i : itemList){
			if(maxDateItem > i.getLastUpdated())
				itemList.remove(i); // Removes items that are less up to date
		}
		if(itemList.isEmpty())
			return null;
		return itemList.get(0); // Contains only one (if all works)
	}
}
