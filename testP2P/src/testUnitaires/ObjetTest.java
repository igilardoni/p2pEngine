package testUnitaires;

import static org.junit.Assert.*;
import model.Objet;
import model.User;

import org.junit.Test;

public class ObjetTest
{

	@Test
	public void testGetResume()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		assertEquals("Je vend des tests unitaires",ob.getResume());
	}

	@Test
	public void testSetResume()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ob.setResume("Nouveau resume");
		assertEquals("Nouveau resume",ob.getResume());
	}

	@Test
	public void testGetDesc()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		assertEquals("Je vend des tests unitaires pas cher",ob.getDesc());
	}

	@Test
	public void testSetDesc()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		String desc = "Je vend des tests unitaires pas cher pas cher";
		ob.setDesc(desc);
		assertEquals("Je vend des tests unitaires pas cher pas cher",ob.getDesc());
	}

	@Test
	public void testGetImg()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		assertEquals("",ob.getImg());
	}

	@Test
	public void testSetImg()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ob.setImg("o");
		assertEquals("o",ob.getImg());
	}

	@Test
	public void testIsProposition()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		assertTrue(ob.isProposition());
	}

	@Test
	public void testSetProposition()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ob.setProposition(false);
		assertFalse(ob.isProposition());
	}

	@Test
	public void testIsSouhait()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		assertFalse(ob.isSouhait());
	}

	@Test
	public void testSetSouhait()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ob.setSouhait(true);
		assertTrue(ob.isSouhait());
	}

	@Test
	public void testIsTroc()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		assertTrue(ob.isTroc());
	}

	@Test
	public void testSetTroc()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ob.setTroc(true);
		assertTrue(ob.isTroc());
	}

	@Test
	public void testIsVente()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		assertFalse(ob.isVente());
	}

	@Test
	public void testSetVente()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ob.setVente(true);
		assertTrue(ob.isVente());
	}

	@Test
	public void testGetUser()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		assertEquals(user,ob.getUser());
	}

	@Test
	public void testSetUser()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		User user1 = new User("REMY", "Julien", "Aubagne", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ob.setUser(user1);
		assertEquals(user1,ob.getUser());
	}

	@Test
	public void testSetTitre()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet ob = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ob.setTitre("Programme");
		assertEquals("Programme", ob.getTitre());
	}
}


