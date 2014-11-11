package testUnitaires;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.Objet;
import model.ObjetsManagement;
import model.User;

import org.junit.Test;

public class ObjetsManagementTest
{
	@Test
	public void testAdd()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		assertTrue(listeObj.add(objet));
	}

	@Test
	public void testDeleteObjet()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet ob = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(ob);
		assertTrue(listeObj.delete(ob));
	}

	@Test
	public void testDeleteInt()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		listeObj.add(objet);
		assertTrue(listeObj.delete(0));
	}

	@Test
	public void testSetPosObjetInt()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		
		listeObj.setPos(objet,0);
		listeObj.setPos(objet1,1);
		
		assertEquals(objet1,listeObj.get(1));
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
	
		listeObj.add(objet);
		assertEquals(objet,listeObj.get(0));
	}


	@Test
	public void testSize()
	{
		ObjetsManagement listeObj = new ObjetsManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		Objet objet = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		Objet objet1 = new Objet(true, false, true, false, "patates", "des bonnes patates", "les meilleurs patates du pays", "", user);
		
		listeObj.add(objet);
		listeObj.add(objet1);
		
		assertEquals(2,listeObj.size());
	}

}
