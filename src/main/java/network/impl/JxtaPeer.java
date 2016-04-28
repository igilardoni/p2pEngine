package network.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import network.api.Node;
import network.api.Peer;
import network.api.Service;
import network.utils.IpChecker;

public class JxtaPeer implements Peer{

	private Node node;
	private HashMap<String, Service> services;
	
	/**
	 * Create a new Peer (Jxta implementation)
	 */
	public JxtaPeer() {
		node = new JxtaNode();
		services = new HashMap<>();
	}
	
	@Override
	public void start(String cache, int port) throws IOException {
		node.initialize(cache, "sxp peer", true);
		node.start(port);
	}

	@Override
	public void stop() {
		node.stop();
	}

	@Override
	public String getIp() {
		try {
			return IpChecker.getIp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Service> getServices() {
		return services.values();
	}

	@Override
	public Service getService(String name) {
		return services.get(name);
	}

	@Override
	public void addService(Service service) {
		services.put(service.getName(), service);
	}

}
