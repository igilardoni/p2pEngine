package model.network;


import static org.junit.Assert.*;
import org.junit.Test;

public class NetworkTest {

	@Test
	public void groupCreating() {
		Network network = new Network(7905, ".jxtaTest", "TestPeer");
		network.start();
		network.addGroup("objet");
		assertEquals("objet", network.getGroup("objet").getPeerGroupName());
	}
}
