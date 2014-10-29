package testUnitaires;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.Rechercher;

public class RechercherTest
{
	@Test
	public void testValidate()
	{
		Rechercher recherche = new Rechercher("Julien", true, false);
		assertFalse(recherche.validate());
	}

	@Test
	public void testProcess()
	{
		Rechercher recherche = new Rechercher("Julien", true, false);
		assertFalse(recherche.process());
	}

}
