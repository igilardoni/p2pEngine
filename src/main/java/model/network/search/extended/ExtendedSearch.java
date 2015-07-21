package model.network.search.extended;

import com.sun.mail.imap.protocol.Item;

import net.jxta.discovery.DiscoveryService;
import model.network.NetworkInterface;

public class ExtendedSearch {

	private String exp;
	private DiscoveryService ds;
	
	public ExtendedSearch(String exp, NetworkInterface n) {
		this.exp = exp;
		this.ds = n.getGroup(Item.class.getSimpleName()).getDiscoveryService();
	}
	
	
	
}
