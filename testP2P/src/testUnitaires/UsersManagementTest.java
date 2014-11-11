package testUnitaires;

import static org.junit.Assert.*;
import model.User;
import model.UsersManagement;

import org.junit.Test;

public class UsersManagementTest
{

	@SuppressWarnings("deprecation")
	@Test
	public void testGetUsersNames()
	{
		UsersManagement userM = new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		String tableau[] = {user.getLogin()};
		userM.addUser(user);
		assertEquals(tableau, userM.getUsersNames());
	}

	@Test
	public void testAddUser()
	{
		UsersManagement userM = new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		assertTrue(userM.addUser(user));
	}

	@Test
	public void testDeleteUser()
	{
		UsersManagement userM = new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		User user2 = new User("Pablo", "Kevin", "Marseille", "0698074315", "julien@live.fr", "PabloKevin", "genielogiciel");
		userM.addUser(user);
		userM.addUser(user2);
		assertTrue(userM.deleteUser("julien1", "genielogiciel"));
	}

	@Test
	public void testCheckLogin()
	{
		UsersManagement userM = new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		User user2 = new User("Pablo", "Kevin", "Marseille", "0698074315", "julien@live.fr", "PabloKevin", "genielogiciel");
		userM.addUser(user);
		userM.addUser(user2);
		assertTrue(userM.checkLogin("julien1", "genielogiciel"));
	}

	@Test
	public void testGetUser()
	{
		UsersManagement userM = new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		userM.addUser(user);
		assertEquals(user, userM.getUser("julien1", "genielogiciel"));
	}

	@Test
	public void testConnectUser()
	{
		UsersManagement userM = new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		userM.addUser(user);
		assertTrue(userM.connectUser("julien1","genielogiciel"));
	}

	@Test
	public void testDisconnectUser()
	{
		UsersManagement userM = new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		userM.addUser(user);
		assertFalse(userM.disconnectUser());
	}

	@Test
	public void testUserExists()
	{
		UsersManagement userM = new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		userM.addUser(user);
		assertTrue(userM.userExists("julien1"));
	}

	@Test
	public void testGetConnectedUser()
	{
		fail("testGetConnectedUser");
	}

}
