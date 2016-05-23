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
		Search<Item> s = new Search<Item>(manager.getNetwork(), Item.class.getSimpleName(), "itemKey", true);
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
			i.publish(manager.getNetwork());
		}
	}


}
