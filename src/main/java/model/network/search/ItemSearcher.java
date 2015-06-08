package model.network.search;

import java.util.ArrayList;

import model.data.item.Item;
import model.data.user.User;
import model.network.NetworkInterface;
import util.VARIABLES;

public class ItemSearcher {
	private NetworkInterface network;
	
	public ItemSearcher(NetworkInterface network){
		this.network = network;
	}
	
	public Item search(String itemKey){
		ArrayList<Item> items;
		User user = null;
		
		Search<Item> itemSearch = new Search<Item>(network.getGroup("items").getDiscoveryService(), "itemKey", true);
		itemSearch.search(itemKey, VARIABLES.MaxTimeSearch, VARIABLES.ReplicationsAccount);
		items = itemSearch.getResults();
		long maxDateItem = 0;
		for(Item i : items){
			if(user==null || user.getKeys().getPublicKey().toString(16).equals(i.getOwner())){
				Search<User> userSearch = new Search<User>(network.getGroup("users").getDiscoveryService(), "publicKey", true);
				userSearch.search(i.getOwner(), VARIABLES.MaxTimeSearch, VARIABLES.ReplicationsAccount);
				ArrayList<User> userList = userSearch.getResults();
				long maxDateUser = 0;
				for (User u : userList) {
					if(!u.checkSignature(u.getKeys())){
						userList.remove(u);
						continue;
					}
					if(maxDateUser > u.getLastUpdated()){
						userList.remove(u);
						continue;
					}
					maxDateUser = u.getLastUpdated();
				}
				if(userList.isEmpty()){
					items.remove(i);
					continue;
				}
				user = userList.get(0);
			}
			if(!i.checkSignature(user.getKeys())){
				items.remove(i);
				continue;
			}
			if(maxDateItem > i.getLastUpdated()){
				items.remove(i);
				continue;
			}
			maxDateItem = i.getLastUpdated();
		}
		if(items.isEmpty())
			return null;
		return items.get(0);
	}
}
