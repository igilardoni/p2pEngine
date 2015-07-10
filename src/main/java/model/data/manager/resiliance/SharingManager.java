package model.data.manager.resiliance;

import java.util.ArrayList;
import java.util.Enumeration;

import model.data.manager.Manager;
import model.data.user.User;
import model.network.NetworkInterface;
import model.network.communication.Communication;
import model.network.search.RandomPeerFinder;
import model.network.search.Search;
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
	private int checkTime; //intervals for checking data's replication
	private boolean continueThread = false; //boolean to start/stop the thread.
	private Thread thread = null;
	private ArrayList<AbstractResiliance> resiliances = new ArrayList<AbstractResiliance>();
	
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
	 * @param checkTime The amount of time (in minutes) between 2 data checks/replication
	 */
	public SharingManager(Manager manager, NetworkInterface network, Communication com, int replications, int checkTime) {
		this.checkTime = checkTime * 60 * 1000;
	}
	
	public void addResiliance(AbstractResiliance r) {
		resiliances.add(r);
	}
	
	public void resiliance() {
		for(AbstractResiliance r : resiliances) {
			r.step();
		}
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
						resiliance();
					try {
						Thread.sleep(checkTime);
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
}
