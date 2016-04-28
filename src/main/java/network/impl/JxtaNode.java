package network.impl;

import java.io.File;
import java.io.IOException;

import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import network.api.Node;

/**
 * Default JXTA Node implementation. Represent the server node.
 * When successfully started, yield an Advertisement that represent this node.
 * @see Node
 * @author Julien Prudhomme
 */
public class JxtaNode implements Node{

	private NetworkManager networkManager = null;
	private boolean initialized = false;
	private PeerGroup defaultPeerGroup = null;
	
	@Override
	public void initialize(String cacheFolder, String name, boolean persistant) throws IOException {
		File configFile = new File("." + System.getProperty("file.separator") + cacheFolder); /* file used by the networkManager */
		networkManager = initializeNetworkManager(configFile, name, persistant);
		//no errors
		initialized = true;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public void start(int port) throws RuntimeException {
		if(!initialized) {
			throw new RuntimeException("Node must be initalized before start call");
		}
		try {
			NetworkConfigurator configurator = networkManager.getConfigurator();
			configurator.setTcpPort(port);
			configurator.setHttpPort(port + 1);
			defaultPeerGroup = networkManager.startNetwork();
			//Switch to rendez vous mode if possible, check every 60 secs
			defaultPeerGroup.getRendezVousService().setAutoStart(true,60*1000);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error on config file");
		} catch (PeerGroupException e) {
			e.printStackTrace();
			System.err.println("error while creating main peer group");
			System.exit(-1);
			//can't continue 
		}
		
	}
	
	@Override
	public boolean isStarted() {
		return isInitialized() && networkManager.isStarted();
	}
	
	/**
	 * Initialize the network manager
	 * @param configFile
	 * @param peerName
	 * @param persistant
	 * @return
	 * @throws IOException
	 */
	private NetworkManager initializeNetworkManager(File configFile, String peerName, boolean persistant) throws IOException {
		NetworkManager manager = null;
		NetworkConfigurator configurator = null;
		manager = new NetworkManager(NetworkManager.ConfigMode.EDGE, peerName, configFile.toURI()); /* Setting network */
		configurator = manager.getConfigurator(); /* Getting configurator for future tweaks */
        configurator.setTcpEnabled(true);
        configurator.setHttpEnabled(true);
        configurator.setTcpIncoming(true);
        configurator.setHttpIncoming(true);
        configurator.setHttpOutgoing(true);
        configurator.setTcpOutgoing(true);
        configurator.setUseMulticast(true);
		configurator.setTcpInterfaceAddress("0.0.0.0");
		// TODO configurator.setTcpPublicAddress(IpChecker.getIp(), false);
		configurator.setHttpInterfaceAddress("0.0.0.0");
		// TODO configurator.setHttpPublicAddress(IpChecker.getIp(), false);
        configurator.setTcpEndPort(-1);
        configurator.setTcpStartPort(-1);
        configurator.setName("SXPeerGroup");
        configurator.setDescription("SXP default peer group");
        configurator.setPrincipal("SXP peer group");
        manager.setConfigPersistent(persistant);
		return manager;
	}
}
