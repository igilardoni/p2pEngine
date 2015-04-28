package model.user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import util.Hasher;
import util.secure.AsymKeysImpl;

/**
 * Test for User Class
 * @author Michael
 *
 */
public class UserTest {
	private static String nick = "nick";
	private static String password = "password";
	private static String hashPassword = Hasher.SHA256(password);
	private static String name = "name";
	private static String firstName = "firstName";
	private static String email = "email";
	private static String phone = "phone";
	private static long date = System.currentTimeMillis();
	private static AsymKeysImpl key = new AsymKeysImpl(false);
	
	@Test
	public void constructors(){
		User user1,user2,user3;
		
		// Three type of constructor
		user1 = new User(nick, password, name, firstName, email, phone);
		user1.setKey(key);
		user2 = new User(nick, hashPassword, name, firstName, email, phone, date, key);
		user3 = new User(nick, hashPassword, name, firstName, email, phone, date,  key.getPublicKey(), key.getP(), key.getG());
		
		assertEquals(user1.compareTo(user2), 0);
		assertEquals(user2.compareTo(user3), 0);
		assertEquals(user3.compareTo(user1), 0);
		
		// Compare with empty key and not empty key (different expected)
		user1.setKey(null);
		assertEquals(user1.compareTo(user2), 1);
		assertEquals(user1.compareTo(user3), 1);
		
		// Compare two user with empty key (equals expected)
		user2.setKey(null);
		assertEquals(user1.compareTo(user2), 0);
		
		// Compare empty constructor with user with empty key (equals expected)
		user1 = new User();
		assertEquals(user1.compareTo(user2), 0);
		
		// Compare empty constructor with user with not empty key (different expected)
		assertEquals(user1.compareTo(user3), 1);
	}
	
	@Test
	public void isPassword(){
		User user;
		user = new User(nick, hashPassword, name, firstName, email, phone, date, key);
		assertEquals(user.isPassword(password), true);
		assertEquals(user.isPassword(password+"#"), false);
	}
}
