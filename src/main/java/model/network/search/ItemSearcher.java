package model.network.search;

import java.util.ArrayList;

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
		User user = null;
		
		Search<Item> itemSearch = new Search<Item>(network.getGroup("items").getDiscoveryService(), "itemKey", true);
		itemSearch.search(itemKey, VARIABLES.MaxTimeSearch, VARIABLES.ReplicationsAccount);
		itemList = itemSearch.getResults();
		long maxDateItem = 0;
		for(Item i : itemList){
			if(user==null || !user.getKeys().getPublicKey().toString(16).equals(i.getOwner())){
				// Normally used only once (except if Item i is fallacious)
				Search<User> userSearch = new Search<User>(network.getGroup("users").getDiscoveryService(), "publicKey", true);
				userSearch.search(i.getOwner(), VARIABLES.MaxTimeSearch, VARIABLES.ReplicationsAccount);
				ArrayList<User> userList = userSearch.getResults();
				long maxDateUser = 0;
				for (User u : userList) {
					if(!u.checkSignature(u.getKeys())){
						userList.remove(u); // Removes users that are fallacious
						continue;
					}
					if(maxDateUser >= u.getLastUpdated()){
						userList.remove(u); // Removes users that are less up to date (or as much)
						continue;
					}
					maxDateUser = u.getLastUpdated();
				}
				for(User u : userList){
					if(maxDateUser > u.getLastUpdated())
						userList.remove(u); // Removes users that are less up to date
				}
				if(userList.isEmpty()){
					itemList.remove(i); // Removes items that the user doesn't exist on the network
					continue;
				}
				user = userList.get(0); // Contains only one (if all works)
			}
			if(!i.checkSignature(user.getKeys())){
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
