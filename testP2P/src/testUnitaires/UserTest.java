package testUnitaires;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.Objet;
import model.User;

import org.junit.Test;

public class UserTest
{

	@Test
	public void testGetNom()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		assertEquals("testGetNom","REMY", user.getNom());
	}

	@Test
	public void testSetNom()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.setNom("Frantz");
		assertEquals("testSetNom","Frantz", user.getNom());
	}

	@Test
	public void testGetTel()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		assertEquals("testGetTel","0698074315", user.getTel());
	}

	@Test
	public void testSetTel()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.setTel("0101010101");
		assertEquals("testSetTel","0101010101", user.getTel());
	}

	@Test
	public void testGetMail()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		assertEquals("testGetMail","julien@live.fr", user.getMail());
	}

	@Test
	public void testSetMail()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.setMail("remy-julien@live.fr");
		assertEquals("testSetMail","remy-julien@live.fr", user.getMail());
	}

	@Test
	public void testGetLogin()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		assertEquals("testGetLogin","julien1", user.getLogin());
	}

	@Test
	public void testSetLogin()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.setLogin("julien2");
		assertEquals("testSetLogin","julien2", user.getLogin());
	}

	@Test
	public void testSetPrenom()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.setPrenom("Luc");
		assertEquals("testSetLogin","Luc", user.getPrenom());
	}

	@Test
	public void testGetPrenom()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		assertEquals("testGetPrenom","Julien", user.getPrenom());
	}

	@Test
	public void testGetPasswordHash()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		String password = "genielogiciel";
		int passwordhash = password.hashCode();
		assertEquals("testGetPasswordHash",passwordhash, user.getPasswordHash());
		/*
		User user2 = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "");
		String password2 = "genielogiciel";
		int passwordhash2 = password2.hashCode();
		assertEquals("testGetPasswordHash",passwordhash2, user2.getPasswordHash());*/
	}

	@Test
	public void testIsPasswordEqual()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		String password = "genielogiciel";
		assertTrue("testIsPwdEqual",user.isPasswordEqual(password));
	}

	@Test
	public void testCheckPassword()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		String password = "genielogiciel";
		assertTrue("testCheckPwd",user.checkPassword(password));
	}

	@Test
	public void testGetAdresse()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		assertEquals("testGetAdresse","Marseille", user.getAdresse());
	}

	@Test
	public void testSetAdresse()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.setAdresse("Aubagne");
		assertEquals("testGetAdresse","Aubagne", user.getAdresse());
	}

	@Test
	public void testGetObjets()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet obj = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		assertEquals(obj, user.getObjets());
	}

	@Test
	public void testAddNote()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.addNote(4);
		assertEquals(4, user.getMoyenneNotes());
	}

	@Test
	public void testGetMoyenneNotes()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.addNote(4);
		user.addNote(3);
		user.addNote(5);
		assertEquals(4, user.getMoyenneNotes());
	}

	@Test
	public void testFlushNotes()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		user.addNote(4);
		user.addNote(3);
		user.addNote(5);
		user.flushNotes();
		assertEquals(0, user.getMoyenneNotes());
	}

	@Test
	public void testGetPanier()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet obj = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		assertEquals(obj, user.getPanier());
	}

}
