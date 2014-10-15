package model;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;

/**
 * Tout objet qui peut être diffusable sur le réseau
 * doit implémenter cette interface
 * @author Prudhomme Julien
 *
 */
public interface Advertisable {
	
	/**
	 * Retourne un objet Advertisement correspondant à l'objet
	 * @return Avertisement
	 */
	public Advertisement getAdvertisement();
	
	/**
	 * Publie l'advertisement sur le réseau
	 * @param discovery le discoveryService du groupe
	 */
	public void publish(DiscoveryService discovery);
	
	/**
	 * Supprime l'advertisement des fichiers locaux
	 * @param discovery le discoveryService du groupe
	 */
	public void flush(DiscoveryService discovery);
}
