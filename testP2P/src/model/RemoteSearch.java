package model;

import java.util.*;

import view.Application;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;

public class RemoteSearch<T extends Advertisable> implements DiscoveryListener {
	
	private DiscoveryService discovery;
	private String attribute;
	private ArrayList<T> results;
	private ArrayList<SearchListener> listeners = new ArrayList<SearchListener>();
	
	/**
	 * Faut peut etre changer, si on veut chercher sur plusieur attribut en meme temps .. enfin on verra
	 * @param discovery
	 * @param attribute
	 */
	public RemoteSearch(DiscoveryService discovery, String attribute) {
		this.discovery = discovery;
		this.attribute = attribute;
	}

	public void search(String value) {
		
		results = new ArrayList<T>(); // on recreer / vide la liste
		
		//on ajoute les étoiles pour pas rechercher au mot près ..
		discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, attribute,"*" + value + "*",10, this);
	}
	
	public void addListener(SearchListener l) {
		listeners.add(l);
	}
	
	private void notifyListeners(T founded) {
		for(SearchListener s: listeners) {
			s.searchEvent(founded);
		}
	}
	
	public ArrayList<T> getResults() {
		return results;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		AbstractAdvertisement<T> adv;
		Enumeration<Advertisement> advs = event.getResponse().getAdvertisements();
		while(advs.hasMoreElements()) {
			adv = (AbstractAdvertisement<T>) advs.nextElement();
			T founded = adv.toClass();
			results.add(founded);
			notifyListeners(founded); //on notifie les éléments qui en ont besoin qu'on a trouver un objet.
		}
		
	}

}
