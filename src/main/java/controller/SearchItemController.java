package controller;

import java.util.ArrayList;

import model.Application;
import model.data.item.Item;
import model.network.search.Search;
import model.network.search.SearchListener;

public class SearchItemController implements SearchListener<Item>{

	ArrayList<String >itmes = new ArrayList<>();
	private ArrayList<SearchListener<Item>> listeners = new ArrayList<SearchListener<Item>>();
	
	public void startSearch(String title) {
		Search<Item> s = new Search<Item>(Application.getInstance().getNetwork(), Item.class.getSimpleName(), "title", false);
		s.addListener(this);
		s.search(title, 0, 0);
		System.out.println("recherche en cours: title=" + title);
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
		System.out.println("ok pour searchEvent");
		if(event != null) {
			if(event instanceof Item) {
				System.out.println("ok bitch");
				itmes.add(event.getTitle());
				notifyListeners(event);
			}
		}
	}
	
}
