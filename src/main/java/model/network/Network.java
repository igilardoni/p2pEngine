package model.network;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;

public class Network implements NetworkInterface {
	
	private PeerID peerID;
	private NetworkManager networkManager;
	
	
	/**
	 * Create a new P2P network, setting the port and the 
	 * folder where Jxta store the configuration and his cache.
	 * Define the peer name on the network
	 * @param port Port used by Jxta network
	 * @param folder Folder where Jxta store it need.
	 * @param peerName Peer name on the network
	 */
	public Network(int port, String folder, String peerName) {
		peerID = generatePeerID(peerName); /* Generating an unique PeerID */
		File configFile;
		
		configFile = new File("." + System.getProperty("file.separator") + peerName); /* Used by the networkManager */
		networkManager = networkManagerSetup(configFile, port, peerName); 
		
		
	}

	@Override
	public PeerGroup getGroup(String group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addGroup(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Generate an unique Peer ID from the peer name.
	 * @param peerName A string, generally the peer name, from the PeerID will be generated.
	 * @return
	 */
	private PeerID generatePeerID(String peerName) {
		return IDFactory.newPeerID(PeerGroupID.defaultNetPeerGroupID, peerName.getBytes());
	}
	
	
	private NetworkManager networkManagerSetup(File configFile, int port, String peerName) {
		NetworkManager manager = null;
		NetworkConfigurator configurator = null;
		try {
			manager = new NetworkManager(NetworkManager.ConfigMode.EDGE, peerName, configFile.toURI()); /* Setting network */
			configurator = manager.getConfigurator(); /* Getting configurator to future tweaks */
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		/* Configuration settings */
		 configurator.setTcpPort(port);
         configurator.setTcpEnabled(true);
         configurator.setTcpIncoming(true);
         configurator.setTcpOutgoing(true);
         configurator.setUseMulticast(true);
         configurator.setPeerID(peerID);
         /*configurator.setTcpPublicAddress(IpChecker.getIp(), false); TODO set public adress to make Jxta works on internet */
         try {
			configurator.setTcpInterfaceAddress(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
         configurator.setTcpEndPort(-1);
         configurator.setTcpStartPort(-1);
		
		return manager;
	}
}
