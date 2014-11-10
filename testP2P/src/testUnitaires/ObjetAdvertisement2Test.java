package testUnitaires;

import static org.junit.Assert.*;
import model.Objet;
import model.ObjetAdvertisement;
import model.ObjetAdvertisement2;
import model.User;
import net.jxta.id.ID;

import org.junit.Test;

public class ObjetAdvertisement2Test
{
	@SuppressWarnings("static-access")
	@Test
	public void testGetAdvType()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet obj = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ObjetAdvertisement2 objet = new ObjetAdvertisement2(obj);
		assertEquals(objet.getAdvertisementType(), objet.getAdvType());
	}

	@Test
	public void testgetID() 
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet obj = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ObjetAdvertisement2 objet = new ObjetAdvertisement2(obj);
		assertNull(objet.getID());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetAdvertisementType()
	{
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "remy-julien@live.fr", "Genie", "Logiciel");
		Objet obj = new Objet(true, false, true, false, "Test unitaires", "Je vend des tests unitaires", "Je vend des tests unitaires pas cher", "", user);
		ObjetAdvertisement2 objet = new ObjetAdvertisement2(obj);
		assertEquals("jxta:" + ObjetAdvertisement2.class.getName(), objet.getAdvertisementType());
	}
}
