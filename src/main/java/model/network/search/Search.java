package model.network.search;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import model.advertisement.AbstractAdvertisement;

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
	
	public class Result {
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
	 * @param discovery
	 * @param attribute
	 * @param exact true if the search has to be exact (letter sensitive) or not (search "foo" can find "foobar")
	 */
	public Search(DiscoveryService discovery, String attribute, boolean exact) {
		this.discovery = discovery;
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
		String searchValue = exact ? "*" + value + "*": value;
		discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, attribute, searchValue, 100, this);
		
		long waiting = maxWaitTime;
		
		if(maxWaitTime != 0) {
			while(waiting > 0 && results.size() < waitResult) {
				long currentTime = System.currentTimeMillis();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
	
	public ArrayList<T> getResults() {
		return results;
	}
	
	public ArrayList<Result> getResultsWithPeerID() {
		return this.resultsWithPeerID;
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
