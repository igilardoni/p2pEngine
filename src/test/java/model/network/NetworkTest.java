package model.network;


import static org.junit.Assert.*;

import org.junit.Test;

public class NetworkTest {

	
	/*
	 * Check if the group is created, reachable and have the correct name.
	 */
	@Test
	public void groupCreating() {
		Network network = new Network(7905, ".jxtaTest", "TestPeer");
		network.start();
		network.addGroup("objet");
		assertEquals("objet", network.getGroup("objet").getPeerGroupName());
		network.stop();
	}
}
