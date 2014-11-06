package model;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;


/**
 * Pour obtenir une ressource distante, ne pas utiliser pour faire de la recherche (fonction bloquante !)
 * Utile pour obtenir une ressource que l'on pense unique et que l'on veut un seul résultat.
 * @author Prudhomme Julien
 *
 * @param <T>
 */
public class RemoteRessource<T extends Advertisable> implements DiscoveryListener {
	
	private DiscoveryService discovery;
	private String attribute;
	private T result = null;
	private long waitTime;
	private String fromPeerID = null;
	
	/**
	 * Obtenir un advertisement (une ressource) particulière sur le réseau : par exemple un utilisateur unique
	 * @param discovery
	 * @param attribute
	 * @param waitTime temps d'attente maximum
	 */
	public RemoteRessource(DiscoveryService discovery, String attribute, long waitTime) {
		this.discovery = discovery;
		this.attribute = attribute;
		this.waitTime = waitTime;
	}
	
	public T getRemoteRessource(final String value) {
		final RemoteRessource<T> thisInstance = this;
		result = null;
		discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, attribute, value, 1, thisInstance);
		
		long waiting = waitTime;
		
		while(waiting > 0 && result == null) {
			long currentTime = System.currentTimeMillis();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			waiting -= System.currentTimeMillis()-currentTime;
		}
		
		T res = result;
		result = null;
		return res;
	}
	
	public String getLastRequestSource() {
		if(fromPeerID == null) System.out.println("prob ici");
		return fromPeerID;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		this.fromPeerID = "urn:jxta:" + event.getSource().toString().substring(7);
		AbstractAdvertisement<T> adv = (AbstractAdvertisement<T>) event.getResponse().getAdvertisements().nextElement();
		result = adv.toClass();
	}
}
