package network.factories;

import java.io.IOException;

import network.api.Peer;
import network.impl.jxta.JxtaPeer;

/**
 * {@link Peer} factory
 * @author Julien Prudhomme
 *
 */
public class PeerFactory {
	
	/**
	 * create the default implementation of {@link Peer}
	 * @return a {@link Peer}
	 */
	public static Peer createDefaultPeer() {
		return createJxtaPeer();
	}
	
	/**
	 * Create a the default implementation of {@link Peer} and start it
	 * @return an initialized and started {@link Peer}
	 */
	public static Peer createDefaultAndStartPeer() {
		return createAndStartPeer("jxta");
	}
	
	/**
	 * Create a Jxta implementation of {@link Peer}
	 * @return a {@link JxtaPeer}
	 */
	public static JxtaPeer createJxtaPeer() {
		return new JxtaPeer();
	}
	
	/**
	 * Create, initialize, and start a {@link JxtaPeer}
	 * @return an initialized and started {@link Peer}
	 */
	public static Peer createAndStartPeer(String impl) {
		Peer peer;
		switch(impl) {
		case "jxta": peer = createJxtaPeer(); break;
		default: throw new RuntimeException(impl + "doesn't exist");
		}
		try {
			peer.start(".peercache", 9564);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return peer;
	}
}
