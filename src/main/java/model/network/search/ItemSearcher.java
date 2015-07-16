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
	 * TODO All to change !
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
