package testUnitaires;

import static org.junit.Assert.*;
import model.Objet;
import model.ObjetsManagement;
import model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ObjetsManagementTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testAdd()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		assertEquals(listeObj.get(0), objet);
	}

	@Test
	public void testDeleteObjet()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet2 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		listeObj.add(objet1);
		listeObj.add(objet2);
		listeObj.delete(objet);
		assertEquals(listeObj.get(0), objet1);
	}

	@Test
	public void testDeleteInt()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet2 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		listeObj.add(objet1);
		listeObj.add(objet2);
		listeObj.delete(1);
		assertEquals(listeObj.get(1), objet2);
	}

	@Test
	public void testSetPosObjetInt()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet2 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		listeObj.add(objet1);
		listeObj.add(objet2);
		listeObj.setPos(objet1,0);
		assertEquals(listeObj.get(0),objet1);
	}

	@Test
	public void testSetPosIntInt()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet2 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		listeObj.add(objet1);
		listeObj.add(objet2);
		listeObj.setPos(1,0);
		assertEquals(listeObj.get(0),objet1);
	}

	@Test
	public void testUpPos()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet2 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		listeObj.add(objet1);
		listeObj.add(objet2);
		listeObj.upPos(objet2);
		assertEquals(listeObj.get(1),objet2);
	}

	@Test
	public void testDownPos()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet2 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		listeObj.add(objet1);
		listeObj.add(objet2);
		listeObj.downPos(objet1);
		assertEquals(listeObj.get(2),objet1);
	}

	@Test
	public void testGet()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet2 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		listeObj.add(objet1);
		listeObj.add(objet2);
		assertEquals(listeObj.get(0),objet);
	}


	@Test
	public void testSize()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet2 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		listeObj.add(objet1);
		listeObj.add(objet2);
		assertEquals(3,listeObj.size());
	}

}
