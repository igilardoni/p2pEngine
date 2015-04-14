package model.network;


import junit.framework.TestCase;

import org.junit.Test;

public class NetworkTest extends TestCase{
	
	@Test
	public void groupCreatingTest() {
		Network network = new Network(7905, ".jxtaTest", "TestPeer");
		network.start();
		network.addGroup("objet");
		assertEquals("objet", network.getGroup("objet").getPeerGroupName());
	}
}
