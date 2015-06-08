package model.network;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.IpChecker;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.PeerGroupAdvertisement;
import net.jxta.protocol.PipeAdvertisement;

/**
 * Core class, setting Jxta, groups, and network.
 * @author Julien Prudhomme
 *
 */
public class Network implements NetworkInterface {
	private NetworkManager networkManager;
	private HashMap<String, PeerGroup> peergroups = new HashMap<String, PeerGroup> ();
	private PeerGroup defaultGroup;
	private PeerGroup temp = null;
	
	
	/**
	 * Create a new P2P network, setting the port and the 
	 * folder where Jxta store the configuration and his cache.
	 * Define the peer name on the network
	 * @param port Port used by Jxta network
	 * @param folder Folder where Jxta store it need.
	 * @param peerName Peer name on the network
	 */
	public Network(int port, String folder, String peerName) {
		File configFile = new File("." + System.getProperty("file.separator") + folder); /* Used by the networkManager */
		networkManager = networkManagerSetup(configFile, port, peerName);
		networkManager.setConfigPersistent(false);
	}

	@Override
	public PeerGroup getGroup(String group) {
		return this.peergroups.get(group);
	}
	
	public PeerGroup getDefaultGroup() {
		return defaultGroup;
	}

	public void createDefaultGroup() {
		try {
			defaultGroup = networkManager.getNetPeerGroup().newGroup(this.generatePeerGroupID("SXP group"),
					networkManager.getNetPeerGroup().getAllPurposePeerGroupImplAdvertisement(), "SXP group", "SXP group");
			System.out.println("default group generated");
		} catch (PeerGroupException e) {
			System.err.println("impossible de créer le groupe par défault");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void addGroup(final String name) {
		ModuleImplAdvertisement mAdv = null;
		temp = null;
		defaultGroup.getDiscoveryService().getRemoteAdvertisements(null, DiscoveryService.GROUP, 
				"Name", name, 1, new DiscoveryListener() {
					
					@Override
					public void discoveryEvent(DiscoveryEvent event) {
						Enumeration<Advertisement> advs = event.getResponse().getAdvertisements();
						while(advs.hasMoreElements()) {
							System.out.println("groupe trouvï¿½");
							PeerGroupAdvertisement adv = (PeerGroupAdvertisement) advs.nextElement();
							System.out.println("nom du groupe : " + adv.getName());
							try {
								//temp = defaultGroup.newGroup(adv);
								temp = defaultGroup.newGroup(adv);
								System.out.println("group joinded");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				});
		try {
			Thread.sleep(10000);
			System.out.println("waiting for group ...");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(temp == null) {
			try {
				System.out.println("creating new group ..");
				mAdv = defaultGroup.getAllPurposePeerGroupImplAdvertisement(); /* Getting the advertisement of implemented modules */
				temp = defaultGroup.newGroup(generatePeerGroupID(name), mAdv, name, name); /* creating & publishing the group */
				getDefaultGroup().getDiscoveryService().remotePublish(temp.getPeerGroupAdvertisement());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			temp.startApp(new String[0]);
		}
		peergroups.put(name, temp);
	}

	@Override
	public void start() {
		try {
			networkManager.startNetwork(); /* Starting the network and JXTA's infrastructure. */
			createDefaultGroup();
			System.out.println("GroupName : " + defaultGroup.getPeerGroupName());
			System.out.println("waiting for rendez vous.");
			defaultGroup.getRendezVousService().setAutoStart(true, 60*1000); /* Switching to RendezVousMode if needed. Check every 60s */
			if(networkManager.waitForRendezvousConnection(5000)) {
				System.out.println("rendez vous found");
			}
			else {
				System.out.println("no rendez vous ...");
			}
		} catch (PeerGroupException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void stop() {
		networkManager.stopNetwork();
	}
	
	/**
	 * Generate an unique PeerGroup ID from the peer group name
	 * @param peerGroupName A string, generally the peer name, from the PeerID will be generated.
	 * @return the newly generated PeerID
	 */
	private PeerGroupID generatePeerGroupID(String peerGroupName) {
		return IDFactory.newPeerGroupID(PeerGroupID.defaultNetPeerGroupID, peerGroupName.getBytes());
	}
	
	
	/**
	 * Setup the networkManager that will store data in configFile folder.
	 * @param configFile The file where the network manager will put or retrieve datas.
	 * @param port The port used by JXTA to communicate.
	 * @param peerName The new future peer name.
	 * @return
	 */
	private NetworkManager networkManagerSetup(File configFile, int port, String peerName) {
		NetworkManager manager = null;
		NetworkConfigurator configurator = null;
		try {
			manager = new NetworkManager(NetworkManager.ConfigMode.EDGE, peerName, configFile.toURI()); /* Setting network */
			configurator = manager.getConfigurator(); /* Getting configurator for future tweaks */
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		/* Configuration settings */
		 configurator.setTcpPort(port);
         configurator.setTcpEnabled(true);
         configurator.setHttpPort(port+10);
         configurator.setHttpEnabled(true);
         configurator.setTcpIncoming(true);
         configurator.setHttpIncoming(true);
         configurator.setHttpOutgoing(true);
         configurator.setTcpOutgoing(true);
         configurator.setUseMulticast(true);
        /* try {
			configurator.setTcpPublicAddress(IpChecker.getIp(), false);
			configurator.setHttpPublicAddress(IpChecker.getIp(), false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
         try {
			configurator.setTcpInterfaceAddress(InetAddress.getLocalHost().getHostAddress());
			configurator.setTcpPublicAddress(IpChecker.getIp(), false);
			configurator.setHttpInterfaceAddress(InetAddress.getLocalHost().getHostAddress());
			configurator.setHttpPublicAddress(IpChecker.getIp(), false);
		//  configurator.setTcpPublicAddress(IpChecker.getIp(), false);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         configurator.setTcpEndPort(-1);
         configurator.setTcpStartPort(-1);
         configurator.setName("SXPeerGroup");
         configurator.setDescription("SXP default peer group");
         configurator.setPrincipal("SXP peer group");
		
		return manager;
	}
	
	/**
	 * Set the JXTA's logger's level
	 * @param level
	 */
	public void setLogger(Level level) {
		Logger.getLogger("net.jxta").setLevel(level);
	}

	@Override
	public boolean isStarted() {
		return this.networkManager.isStarted();
	}
	
	
	/**
	 * Generate an advertisement for a new Pipe
	 * @param id the Pipe's id
	 * @param is_multicast the pipe's type.
	 * @return a PipeAdvertisement.
	 */
	public static PipeAdvertisement getPipeAdvertisement(PipeID id, boolean is_multicast) {
        PipeAdvertisement adv = (PipeAdvertisement )AdvertisementFactory.
            newAdvertisement(PipeAdvertisement.getAdvertisementType());
        adv.setPipeID(id);
        if (is_multicast)
            adv.setType(PipeService.PropagateType); 
        else 
            adv.setType(PipeService.UnicastType); 
        adv.setName("Pipe");
        adv.setDescription("...");
        return adv;
    }
	
	public void addRendezVous(String adress) {
		URI theSeed = URI.create(adress);
		try {
			networkManager.getConfigurator().addSeedRendezvous(theSeed);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		networkManager.setUseDefaultSeeds(false);
	}
	
	public String getBootStrapIp() {
		try {
			return "tcp://" + this.networkManager.getConfigurator().getTcpPublicAddress() + ":" + networkManager.getConfigurator().getTcpPort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
