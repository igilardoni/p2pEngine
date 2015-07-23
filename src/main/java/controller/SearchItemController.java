package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Application;
import model.data.item.Category;
import model.data.item.Category.CATEGORY;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.user.User;
import model.network.search.Search;
import model.network.search.SearchListener;

public class SearchItemController implements SearchListener<Item>{

	private HashMap<String, Item> items;
	private ArrayList<SearchListener<Item>> listeners = new ArrayList<SearchListener<Item>>();
	
	public void startSearch(String title) {
		Search<Item> s = new Search<Item>(Application.getInstance().getNetwork(), Item.class.getSimpleName(), "title", false);
		s.addListener(this);
		items = new HashMap<String, Item>();
		s.search(title, 0, 0);
		System.out.println("recherche en cours: title = '" + title + "'");
	}
	
	public void addListener(SearchListener<Item> l) {
		listeners.add(l);
	}
	
	private void notifyListeners(Item i) {
		for(SearchListener<Item> l : listeners) {
			l.searchEvent(i);
		}
	}

	@Override
	/**
	 * Receive an Item and filtering it.
	 */
	public void searchEvent(Item event) {
		if(!event.checkSignature(event.getKeys())) {
			System.out.println("Item: " + event.getTitle() + " bad signature");
			return;
		}
		if(items.containsKey(event.getItemKey())) {
			Item i = items.get(event.getItemKey());
			if(event.getLastUpdated() > i.getLastUpdated()) {
				items.put(event.getItemKey(), event);
				notifyListeners(event);
			}
		}
		else { 
			items.put(event.getItemKey(), event);
			notifyListeners(event); 
		}
	
		
		
	}
	
	public static void main(String[] args) {
		User user1 = new User("test", "password", "name", "firstname", "email@em.fr", "0650507121");
		user1.getKeys().decryptPrivateKey("password");
		user1.sign(user1.getKeys());
		if(user1.checkSignature(user1.getKeys())) {
			System.out.println("Signature user1 ok");
		}
		
		User user2 = new User(user1.toString());
		if(user2.checkSignature(user2.getKeys())) {
			System.out.println("signature user 2 ok");
		}
		
		Item item1 = new Item(user1, "patate", new Category(CATEGORY.Appliances), "slt vous", null, 
				"france", "contactme", System.currentTimeMillis(), System.currentTimeMillis() + 3600*1000, TYPE.OFFER);
		item1.sign(user1.getKeys());
		if(item1.checkSignature(item1.getKeys())) {
			System.out.println("signature item1 ok");
		}
		Item item2 = new Item(item1.toString());
		if(item2.checkSignature(item2.getKeys())) {
			System.out.println("signature item2 ok");
		}
		System.out.println(item2.getSignature());
	}
	
}
