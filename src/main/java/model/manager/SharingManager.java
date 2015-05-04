package model.manager;

import java.util.ArrayList;

import model.network.NetworkInterface;
import model.network.search.Search;
import model.user.User;

/**
 * Manager for sharing ressource (users, items ..) when needed.
 * Also check data resiliance (replication) on the network.
 * @author Julien Prudhomme
 *
 */
public class SharingManager {
	private Manager manager; //local manager
	private NetworkInterface network; //Jxta network
	private int replications; //number of account replication on the network
	private int checkTime; //intervals for checking data's replication
	private boolean continueThread = false; //boolean to start/stop the thread.
	private Thread thread = null;
	
	
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
	public SharingManager(Manager manager, NetworkInterface network, int replications, int checkTime) {
		this.manager = manager;
		this.network = network;
		this.replications = replications;
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
						Thread.sleep(checkTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
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
	 *  Check user data replication on the network.
	 * @param publicKey The user to check.
	 */
	private void checkUserResilience(String publicKey) {
		Search<User> search = new Search<User>(network.getGroup("users").getDiscoveryService(), "publicKey", true);
		// Wait 3 seconds or "replications" results
		search.search(publicKey, 3000, this.replications);
		ArrayList<User> recurrentUser = search.getResults();
		long maxDate = 0;
		for (User user : recurrentUser) {
			if(!user.checkSignature(user.getKeys())){
				recurrentUser.remove(user);
			}else{
				maxDate = Long.compare(maxDate, user.getLastUpdated()) >= 0 ? maxDate : user.getLastUpdated();
			}
		}
		for (User user : recurrentUser) {
			if(user.getLastUpdated() < maxDate){
				// TODO Mise a jour du peer qui a envoye ce compte OU envoie d'un arret de diffusion.
			}
		}
		for(int i = 0 ; i < (recurrentUser.size() - this.replications) ; i++){
			// TODO Envoyer une copie du compte a un peer aleatoire
		}
	}
	
	public void checkDataResilience() {
		for(User u: manager.getUsers()) {
			checkUserResilience(u.getKeys().getPublicKey().toString(16));
		}
	}
	
}
