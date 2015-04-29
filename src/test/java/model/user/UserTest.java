package model.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;

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
	public void password(){
		User user1 = new User(nick, password, name, firstName, email, phone);
		assertEquals(user1.getHashPwd(), hashPassword);
		assertEquals(user1.getClearPwd(), password);
		
		// Expected ClearPassword null in user2 construct with XML 
		User user2 = new User(user1.toString());
		assertEquals(user1.getClearPwd().equals(user2.getClearPwd()), false);
	}
	
	@Test
	public void constructors(){
		User user1,user2,user3;
		
		// Three type of constructor
		user1 = new User(nick, password, name, firstName, email, phone);
		user1.setKey(key);
		user2 = new User(user1.toString());
		user3 = new User(user2.toString());
		
		assertEquals(user1.equals(user2), true);
		assertEquals(user2.equals(user3), true);
		assertEquals(user3.equals(user1), true);
		
		// Compare with empty key and not empty key (different expected)
		user1.setKey(null);
		assertEquals(user1.equals(user2), false);
		assertEquals(user1.equals(user3), false);
		
		// Compare two user with empty key (equals expected)
		user2.setKey(null);
		assertEquals(user1.equals(user2), true);
		
		// Compare empty constructor with user with empty key (equals expected)
		user1 = new User();
		assertEquals(user1.equals(user2), true);
		
		// Compare empty constructor with user with not empty key (different expected)
		assertEquals(user1.equals(user3), false);
	}
	
	@Test
	public void isPassword(){
		User user;
		user = new User(nick, password, name, firstName, email, phone);
		user.setDate(date);
		assertEquals(user.isPassword(password), true);
		assertEquals(user.isPassword(password+"#"), false);
		
		user = new User(nick, password+"#", name, firstName, email, phone);
		user.setDate(date);
		assertEquals(user.isPassword(password), false);
		assertEquals(user.isPassword(password+"#"), true);
	}
	
	@Test
	public void cryptPrivate(){
		User user1;
		user1 = new User(nick, password, name, firstName, email, phone);
		BigInteger truePrivate = user1.getKeys().getPrivateKey();
		user1.encryptPrivateKey(password);
		
		User user2 = new User(user1.toString());
		user2.decryptPrivateKey(password);
		assertEquals(truePrivate, user2.getKeys().getPrivateKey());
	}
}
