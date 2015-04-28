package search;

import java.util.ArrayList;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
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
	
	
	/**
	 * Initialise a new research on the discovery, on the specified attribute.
	 * @param discovery
	 * @param attribute
	 */
	public Search(DiscoveryService discovery, String attribute) {
		this.discovery = discovery;
		this.attribute = attribute;
	}
	
	/**
	 * Search all the advertisement in this discovery that match the attribute with this value
	 * @param value
	 */
	public void search(String value) {
		
	}
	
	public void addListener(SearchListener<T> l) {
		listeners.add(l);
	}
	
	public void notifyListeners(T event) {
		for(SearchListener<T> l: listeners) {
			l.searchEvent(event);
		}
	}
	
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		// TODO Auto-generated method stub
		
	}

}
