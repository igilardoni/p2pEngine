package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Application;
import model.data.item.Item;
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
		//if(!event.checkSignature(event.getKeys())) return;
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
	
}
