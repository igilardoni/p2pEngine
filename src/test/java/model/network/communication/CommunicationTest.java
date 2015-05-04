package model.network.communication;

import model.network.Network;

import org.junit.Test;

public class CommunicationTest {
	
	/*
	 * Communication instantation should throw an exeption if the network isn't started.
	 */
	
	
	@Test(expected=Exception.class)
	public void exeptionIfNetworkNotStarted() throws Exception {
		Network n = new Network(9705, ".test", "Test");
		new Communication(n);
	}
	
}
