package model.user;

import static org.junit.Assert.*;
import model.advertisement.AbstractAdvertisement;

import org.jdom2.Element;
import org.junit.Test;

public class UserTest {

	@Test
	public void compareTo() {
		User user1, user2;
		user1 = new User("pja35", "pwd", "Arrighi", "Pablo", "pablo.arrighi@univ-amu.fr", "");
		user2 = new User("pja25", "otherPwd", "OtherName", "otherFirstName", "otherEmail", "otherPhone", user1.getPublicKey(), user1.getP(), user1.getG());
		
		assertEquals(user1.compareTo(user2), 0);
		
		user2 = new User("pja35", "pwd", "Arrighi", "Pablo", "pablo.arrighi@univ-amu.fr", "");
		
		assertEquals(user1.compareTo(user2), 1);
	}

	// TODO More test seems be a good idea
}
