package p2pEngine;

import gui.Application;

import java.util.Enumeration;
import java.util.LinkedList;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.document.Advertisement;
import net.jxta.document.MimeMediaType;

//voir searchdemande

public class SearchOffre implements DiscoveryListener {

	private Application app;
	
	public SearchOffre(Application app) {
		this.app = app;
	}
	
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		LinkedList<ObjetAdvertisement> list = new LinkedList<ObjetAdvertisement>();
		Enumeration advs = event.getResponse().getAdvertisements();
		Objets objs = new Objets();
		while(advs.hasMoreElements()) {
			ObjetAdvertisement adv = (ObjetAdvertisement) advs.nextElement();
			System.out.println(adv.getName());
			objs.add(new Offre(adv));
		}
		app.updateSearchOffre(objs);
		
	}

}
