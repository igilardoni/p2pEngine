package model.data.manager.resiliance;

import model.data.item.Item;
import model.data.manager.Manager;
import model.network.communication.Communication;
import model.network.search.Search;
import model.network.search.SearchListener;

public class ItemResiliance extends AbstractResiliance {

	public ItemResiliance(Manager m, Communication c) {
		super(m, c);
	}

	


	@Override
	public void step() {
		Search<Item> s = new Search<Item>(manager.getNetwork().getGroup("items").getDiscoveryService(), "itemKey", true);
		for(Item i : manager.getItemManager().getItems()) {
			s.search(i.getItemKey(), 2, 5);
			for(Item item: s.getResults()) {
				if(!item.checkSignature(manager.getUserManager().getUser(i.getOwner()).getKeys())) {
					
				}
			}
		}
	}


}
