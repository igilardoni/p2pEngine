package testUnitaires;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
		Inscription inscription = new Inscription("Julien", "Genie", "Genie", "Marseille", "remy-julien@live.fr", "0698074315", "REMY", "Julien");
		assertFalse(inscription.process());
	}

}
