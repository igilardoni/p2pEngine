package p2pEngine;

import gui.Application;

import java.util.Enumeration;
import java.util.LinkedList;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.document.Advertisement;
import net.jxta.document.MimeMediaType;

//A noter que les objets SearchDemande et SearchOffre sont de mauvaise conception, j'ai pas
// eu le temps d'y réfléchir plus, mais on peut faire bcp mieu

/**
 * 
 * @author Prudhomme Julien
 *
 */

public class SearchDemande implements DiscoveryListener {

	private Application app;
	
	public SearchDemande(Application app) {
		this.app = app;
	}
	
	@Override
	/**
	 * Cette méthode sera appelé automatiquement lorsque JXTA aura trouvé des résultats
	 */
	public void discoveryEvent(DiscoveryEvent event) {
		LinkedList<ObjetAdvertisement> list = new LinkedList<ObjetAdvertisement>();
		//on obtient tout les adv correspondant à la recherche
		Enumeration advs = event.getResponse().getAdvertisements();
		
		Objets objs = new Objets();
		//on les convertit en demande et on les stockent.
		while(advs.hasMoreElements()) {
			objs.add(new Demande((ObjetAdvertisement) advs.nextElement()));
			
		}
		//on les envoit à l'UI
		app.updateSearchDemande(objs);
		
	}

}