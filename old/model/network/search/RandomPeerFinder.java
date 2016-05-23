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
package model.network.search;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import model.network.NetworkInterface;

/**
 * This class retrieve random PeerId from the network.
 * @author Prudhomme Julien
 *
 */
public class RandomPeerFinder implements DiscoveryListener{

	private NetworkInterface network;
	private ArrayList<PeerID> results;
	
	public RandomPeerFinder(NetworkInterface network) {
		this.network = network;
	}
	
	public ArrayList<PeerID> getResults() {
		return results;
	}
	
	/**
	 * Try to find randoms peers on the network.
	 * @param timeout maximal time before function return
	 * @param expectedResults the function return if expectedResults results are found
	 */
	public void findPeers(long timeout, int expectedResults) {
		results = new ArrayList<PeerID>();
		network.getDefaultGroup().getDiscoveryService().getRemoteAdvertisements(null, DiscoveryService.PEER, null, null, 1, this);
		
		long waiting = timeout;
		
		//Wait for maxWaitTime or number of results > waitResult
		while(waiting > 0 && results.size() < expectedResults) {
			long currentTime = System.currentTimeMillis();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waiting -= System.currentTimeMillis()-currentTime;
		}
		
	}


	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		String founded = "urn:jxta:" + event.getSource().toString().substring(7);
		try {
			results.add((PeerID) IDFactory.fromURI(new URI(founded)));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
}
