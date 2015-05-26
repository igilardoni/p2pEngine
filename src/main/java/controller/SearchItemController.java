package controller;

import model.Application;
import model.item.Item;
import model.network.search.Search;
import model.network.search.SearchListener;

public class SearchItemController implements SearchListener<Item>{

	public void startSearch(String title) {
		Search<Item> s = new Search<Item>(Application.getInstance().getNetwork().getGroup("Item").getDiscoveryService(), "title", false);
		s.addListener(this);
		s.search(title, 0, 0);
	}

	@Override
	public void searchEvent(Item event) {
		//Un item a été trouvé.
	}
	
}
