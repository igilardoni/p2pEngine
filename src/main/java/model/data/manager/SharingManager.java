package model.data.manager;

import java.util.ArrayList;
import java.util.Enumeration;

import util.VARIABLES;
import model.data.item.Item;
import model.data.user.User;
import model.network.NetworkInterface;
import model.network.communication.Communication;
import model.network.search.ItemSearcher;
import model.network.search.RandomPeerFinder;
import model.network.search.Search;
import model.network.search.Search.Result;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.peer.PeerID;
import net.jxta.protocol.PeerGroupAdvertisement;

/**
 * Manager for sharing ressource (users, items ..) when needed.
 * Also check data resiliance (replication) on the network.
 * @author Julien Prudhomme
 * @author Michael Dubuis
 * 
 */
public class SharingManager {
	private Manager manager; //local manager
	private NetworkInterface network; //Jxta network
	private int replications; //number of account replication on the network
	private int checkTime; //intervals for checking data's replication
	private boolean continueThread = false; //boolean to start/stop the thread.
	private Thread thread = null;
	private Communication com = null;
	
	// TODO add verify Favorites
	/**
	 * Create a new Sharing manager.
	 * The sharing manager will automaticelly check if the datas (users accounts) are
	 * enough replicated on the network.
	 * Call startSharing to start the sharing thread.
	 * You should call the stopSharing method before exiting the application.
	 * @param manager The local ressource manager.
	 * @param network The network interface with Jxta.
	 * @param replications Number of account replication that would be enough on the network.
	 * @param checkTime The amount of time (in minutes) between 2 data replication
	 */
	public SharingManager(Manager manager, NetworkInterface network, Communication com, int replications, int checkTime) {
		this.manager = manager;
		this.network = network;
		this.replications = replications;
		this.com = com;
		this.checkTime = checkTime;
	}
	
	/**
	 * Start 
	 */
	public void startSharing() {
		if(thread != null) {
			throw new RuntimeException("Thread is already running");
		}
		this.continueThread = true;

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while(continueThread) {
						checkDataResilience();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
		thread.start();
	}
	
	public void stopSharing() {
		continueThread = false;
		if(thread == null) return;
		while(thread.isAlive()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread = null;
	}
	
	/**
	 * Update user's (who have this publicKey) Favorites.
	 * @param publicKey
	 */
	public void checkLifeFavorites(String publicKey){
		ItemSearcher searcher = new ItemSearcher(network);
		for(String itemKey : manager.getUserFavorites(publicKey).getItemsKey()){
			Item i = searcher.search(itemKey);
			manager.getUserFavorites(publicKey).updateItem(itemKey, i);
		}
		if(manager.getCurrentUser().getKeys().getPublicKey().toString(16).equals(publicKey))
			manager.getUserFavorites(publicKey).sign(manager.getCurrentUser().getKeys());
	}
	
	/**
	 *  Check user data replication on the network.
	 * @param publicKey The user to check.
	 */
	private void checkUserResilience(String publicKey) {
		try {
			Search<User> search = new Search<User>(network.getGroup("users").getDiscoveryService(), "publicKey", true);
			// Wait "checkTime" seconds or "replications" results
			search.search(publicKey, this.checkTime, this.replications);
			ArrayList<Search<User>.Result> results = search.getResultsWithPeerID();
			User user = manager.getUser(publicKey);
			long maxDate = user.getLastUpdated();
			for (Search<User>.Result r : results) {
				if(!r.result.checkSignature(r.result.getKeys())){
					results.remove(r);
				}else{
					if(Long.compare(maxDate, r.result.getLastUpdated()) < 0){
						// If a result is more recent than mine
						maxDate = r.result.getLastUpdated();
						user = r.result;
						if(!manager.getCurrentUser().equals(user))
							manager.addUser(user);
						else{
							// FATAL ERROR : FAILLE DE SECURITE (QUELQU'UN A REUSSI A MODIFIER MON COMPTE)
						}
					}
				}
			}
			for (Search<User>.Result r : results) {
				if(r.result.getLastUpdated() < maxDate){
					// TODO service "updaterUsers"
					com.sendMessage(manager.completUserXMLString(publicKey), "TransmitAccountService", r.peerID);
				}
			}
			if((results.size() - this.replications) > 0){
				RandomPeerFinder finder = new RandomPeerFinder(network);
				finder.findPeers(3000, (results.size() - this.replications));
				PeerID[] randomPeers = new PeerID[finder.getResults().size()]; 
				randomPeers = finder.getResults().toArray(randomPeers);
				com.sendMessage(manager.completUserXMLString(publicKey), "TransmitAccountService", randomPeers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkDataResilience() {
		for(User u: manager.getUsers()) {
			checkUserResilience(u.getKeys().getPublicKey().toString(16));
		}
	}
	
	public void testGroup() {
		System.out.println("Search for existing group ...");
		network.getDefaultGroup().getDiscoveryService().getRemoteAdvertisements(null, DiscoveryService.GROUP, 
				"Name", null, 10, new DiscoveryListener() {

			@Override
			public void discoveryEvent(DiscoveryEvent event) {
				Enumeration<Advertisement> advs = event.getResponse().getAdvertisements();
				while(advs.hasMoreElements()) {
					PeerGroupAdvertisement adv = (PeerGroupAdvertisement) advs.nextElement();
					System.out.println("Groupe trouv� :" + adv.getName());
				}
			}
			
		});
	}
	
}
