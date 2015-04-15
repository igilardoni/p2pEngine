package model.network.communication;

import static org.junit.Assert.assertEquals;
import model.network.Network;

import org.junit.Test;

public class CommunicationTest {
	
	/*
	 * Communication instantation should throw an exeption if the network isn't started.
	 */
	@Test
	public void exeptionIfNetworkNotStarted() {
		Network n = new Network(9705, ".test", "Test");
		boolean ex = false;
		try {
			Communication c = new Communication(n);
		} catch (Exception e) {
			ex = true;
		}
		assertEquals(true, ex);
		
		n.start();
		ex = false;
		try {
			Communication c = new Communication(n);
		} catch (Exception e) {
			ex = true;
		}
		assertEquals(false, ex);
		
	}
}
