package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;


/**
 * Cette classe enregistre des advs trouv�s (dans un buffer) jusqu'a ce qu'on demande de les r�cuperer.
 * @author Prudhomme Julien
 *
 * @param <T>
 */
public abstract class AdvertisementSearch<T extends Advertisement> implements DiscoveryListener{

	protected ArrayList<T> advs = new ArrayList<T>();
	private Peer peer;
	
	public AdvertisementSearch(Peer peer) {
		this.peer = peer;
	}
	
	/**
	 * Recherche un adv sur le réseau
	 * @param key l'attribue de l'adv	
	 * @param value sa valeur
	 * 
	 * Exemple search("titre", "*patate*"); trouve les adv dont un attribut est titre et sa valeur contient patate
	 * 
	 */
	public void search(String key, String value) {
		peer.getDiscovery().getRemoteAdvertisements(null, DiscoveryService.ADV, key, value, 1, this);
	}
	
	/**
	 * Action à faire une fois que la recherche est finie
	 */
	protected abstract void action();
	
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		Enumeration advs = event.getResponse().getAdvertisements();
		while(advs.hasMoreElements()) {
			this.advs.add((T) advs.nextElement());
		}
		action();
	}
	
    /**
     * Retourne tout les advs trouv� et vide le buffer
     * @return
     */
	public ArrayList<T> getAdvs() {
		ArrayList<T> advs = (ArrayList<T>) this.advs.clone();
		this.advs.clear();
		return advs;
	}

}
