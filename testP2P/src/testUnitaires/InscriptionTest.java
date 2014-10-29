package testUnitaires;

import static org.junit.Assert.*;
import model.UsersManagement;

import org.junit.Test;

import view.Application;
import controller.Inscription;

public class InscriptionTest
{

	@Test
	public void testValidate()
	{
		Inscription inscription = new Inscription("Julien", "Genie", "Genie", "Marseille", "remy-julien@live.fr", "0698074315", "REMY", "Julien");
		assertTrue(inscription.validate());
	}

	@Test
	public void testProcess()
	{
		UsersManagement users = Application.getInstance().getUsers();
		Inscription inscription = new Inscription("Julien", "Genie", "Genie", "Marseille", "remy-julien@live.fr", "0698074315", "REMY", "Julien");
		assertFalse(inscription.process());
	}

}
