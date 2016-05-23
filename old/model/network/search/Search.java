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
import java.util.Enumeration;

import model.advertisement.AbstractAdvertisement;
import model.network.NetworkInterface;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.MimeMediaType;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;

/**
 * Search an advertisement on the network.
 * @author Julien Prudhomme
 *
 * @param <T> the advertisement class, that extends AbstractAdvertisement.
 */
public class Search<T extends AbstractAdvertisement> implements DiscoveryListener {

	private ArrayList<SearchListener<T>> listeners = new ArrayList<SearchListener<T>>();
	private DiscoveryService discovery;
	private String attribute;
	private boolean exact;
	private ArrayList<T> results = new ArrayList<T>();
	private ArrayList<Result> resultsWithPeerID = new ArrayList<Result>();
	
	public class Result implements Cloneable{
		public PeerID peerID;
		public T result;
		
		protected Result(String peerID, T result) {
			
			try {
				this.peerID = (PeerID) IDFactory.fromURI(new URI(peerID));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			this.result = result;
		}
		
	}
	
	
	/**
	 * Initialise a new research on the discovery, on the specified attribute.
	 * @param NetworkInterface
	 * @param pg the peerGroup. Usually Class name or id-ClassName
	 * @param attribute
	 * @param exact true if the search has to be exact (letter sensitive) or not (search "foo" can find "foobar")
	 */
	public Search(NetworkInterface n, String pg, String attribute, boolean exact) {
		this.discovery = n.getGroup(pg).getDiscoveryService();
		this.attribute = attribute;
		this.exact = exact;
	}
	
	/**
	 * Search all the advertisement in this discovery that match the attribute with this value
	 * @param value
	 * @param maxWaitTime Time to wait before function returns. Can be 0, the search will continue and notify listeners and function returns immediately
	 * @param waitResult, number of results expected before the function return;
	 */
	public void search(String value, long maxWaitTime, int waitResult) {
		results = new ArrayList<T>();
		String searchValue = !exact ? "*" + value + "*": value;
		discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, attribute, searchValue, 10, this);
		
		long waiting = maxWaitTime;
		
		if(maxWaitTime != 0) {
			while(waiting > 0 && (results.size() < waitResult || waitResult == 0)) {
				long currentTime = System.currentTimeMillis();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				waiting -= System.currentTimeMillis()-currentTime;
			}
		}
	}
		
		//Wait for maxWaitTime or number of results > waitResult

	
	/**
	 * Add a listeners that want to be called when advertisements are reveived.
	 * @param l
	 */
	public void addListener(SearchListener<T> l) {
		listeners.add(l);
	}
	
	private void notifyListeners(T event) {
		for(SearchListener<T> l: listeners) {
			l.searchEvent(event);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<T> getResults() {
		return (ArrayList<T>) results.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Result> getResultsWithPeerID() {
		return (ArrayList<Search<T>.Result>) this.resultsWithPeerID.clone();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		String pid = "urn:jxta:" + event.getSource().toString().substring(7);
		Enumeration<Advertisement> advs = event.getResponse().getAdvertisements();
		while(advs.hasMoreElements()) {
			T adv = (T) advs.nextElement();
			results.add(adv);
			resultsWithPeerID.add(new Result(pid, adv));
			notifyListeners(adv);
		}
		
	}

}
