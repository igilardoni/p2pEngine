package controller;

import java.util.Collection;

import model.entity.Item;
import network.api.Peer;
import network.api.SearchListener;
import network.api.Service;
import network.factories.PeerFactory;
import network.impl.advertisement.ItemAdvertisement;
import network.impl.jxta.JxtaItemService;
import protocol.impl.sigma.ElGamalSign;
import rest.api.Authentifier;
import rest.factories.AuthentifierFactory;
import rest.factories.RestServerFactory;

/**
 * Main class
 * {@link Application} is a singleton
 * @author Julien Prudhomme
 *
 */
public class Application {
	private static Application instance = null;
	private Peer peer;
	private Authentifier auth;
	
	public Application() {
		if(instance != null) {
			throw new RuntimeException("Application can be instanciate only once !");
		}
		instance = this;
	}
	
	public static Application getInstance()	{
		return instance;
	}
	
	public void run() {
		setPeer(PeerFactory.createDefaultAndStartPeer());
		setAuth(AuthentifierFactory.createDefaultAuthentifier());
		RestServerFactory.createAndStartDefaultRestServer(8080); //start the rest api
	}
	
	public void runForTests(int restPort) {
		setPeer(PeerFactory.createDefaultAndStartPeerForTest());
		setAuth(AuthentifierFactory.createDefaultAuthentifier());
		RestServerFactory.createAndStartDefaultRestServer(restPort);
		
		ItemAdvertisement<ElGamalSign> iadv = new ItemAdvertisement<>();
		iadv.setTitle("test");
		
		iadv.publish(Application.getInstance().getPeer());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Service service = getPeer().getService(JxtaItemService.NAME);
		
		while(true) {
			System.out.println("searching ...");
			service.search("title", "test", new SearchListener<ItemAdvertisement<ElGamalSign>>() {

				@Override
				public void notify(Collection<ItemAdvertisement<ElGamalSign>> result) {
					System.out.println("adv found !");
				}
				
			});
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		new Application();
		Application.getInstance().runForTests(8081);
	}

	public Peer getPeer() {
		return peer;
	}

	public void setPeer(Peer peer) {
		this.peer = peer;
	}

	public Authentifier getAuth() {
		return auth;
	}

	public void setAuth(Authentifier auth) {
		this.auth = auth;
	}
}
