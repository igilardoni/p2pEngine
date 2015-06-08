package model.network;

import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.protocol.PeerGroupAdvertisement;

/**
 * A class that represent a peer group with an unique PeerGroupID.
 * @author Julien
 *
 */

public class Group {
	private PeerGroupID PID = null;
	private PeerGroupAdvertisement adv = null;
	private PeerGroup peerGroup = null;
	
	
	public Group(PeerGroup parent, String name) {
		System.out.println(generatePeerGroupID(name));
	}
	
	private PeerGroupID generatePeerGroupID(String peerGroupName) {
		return IDFactory.newPeerGroupID(PeerGroupID.defaultNetPeerGroupID, peerGroupName.getBytes());
	}
	
	public static void main(String[] args) {
		new Group(null, "items");
	}
	
}
