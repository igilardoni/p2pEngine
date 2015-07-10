package model.data.manager.resiliance;

import net.jxta.peer.PeerID;
import model.data.item.Item;
import model.data.manager.Manager;
import model.network.communication.Communication;
import model.network.communication.service.InstanceSender.ItemSender;
import model.network.search.RandomPeerFinder;
import model.network.search.Search;
import model.network.search.SearchListener;

/**
 * Retrieve and spread items on the network.
 * @author crashxxl
 *
 */
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
				if(!item.checkSignature(i.getKeys())) {
					s.getResults().remove(item);
				}
				else {
					if(item.getLastUpdated() > i.getLastUpdated()) {
							manager.getItemManager().addItem(item);
							i = item;
					}
				}
			}
			if(s.getResults().size() < 5) {
				RandomPeerFinder rpf = new RandomPeerFinder(manager.getNetwork());
				rpf.findPeers(2, 5 - s.getResults().size());
				com.getService(ItemSender.class.getSimpleName()).sendMessage(i, rpf.getResults().toArray(new PeerID[0]));
			}
			i.publish(manager.getNetwork().getGroup("items"));
		}
	}


}
