package testUnitaires;

import static org.junit.Assert.*;
import model.communications.Accord;

import org.junit.Test;

public class AccordTest
{

	@Test
	public void testIsAccepted()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		assertFalse(acc.isAccepted());
	}

	@Test
	public void testSetAccepted()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		acc.setAccepted();
		assertTrue(acc.isAccepted());
	}

	@Test
	public void testIsRated()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		assertFalse(acc.isRated());
	}

	@Test
	public void testSetRated()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		acc.setRated();
		assertTrue(acc.isRated());
	}

	@Test
	public void testSetMessageTo()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		acc.setMessageTo("bobo");
		assertEquals("bobo", acc.getMessageTo());
	}

	@Test
	public void testGetFrom()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		assertEquals("bibi", acc.getFrom());
	}

	@Test
	public void testGetTo()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		assertEquals("bobo", acc.getTo());
	}

	@Test
	public void testGetAnnonce()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		assertEquals("patate", acc.getAnnonce());
	}

	@Test
	public void testGetMessageFrom()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		assertEquals("baba", acc.getMessageFrom());
	}

	@Test
	public void testGetMessageTo()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		acc.setMessageTo("bobo");
		assertEquals("bobo", acc.getMessageTo());
	}

}
