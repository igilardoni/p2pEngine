package testUnitaires;

import static org.junit.Assert.*;
import model.User;
import model.UsersManagement;

import org.junit.Test;

import controller.AnnonceDelete;

public class AnnonceDeleteTest
{

	@Test
	public void testValidate()
	{
		UsersManagement users= new UsersManagement();
		User user = new User("REMY", "Julien", "Marseille", "0698074315", "julien@live.fr", "julien1", "genielogiciel");
		users.addUser(user);
		int i = 0;
		AnnonceDelete annonce = new AnnonceDelete(i);
		assertTrue(annonce.validate());
	}

	@Test
	public void testProcess()
	{
		fail("Not yet implemented");
	}

}
