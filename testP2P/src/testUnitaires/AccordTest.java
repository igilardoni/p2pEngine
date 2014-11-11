package testUnitaires;

import static org.junit.Assert.*;
import model.Accord;

import org.junit.Test;

public class AccordTest
{

	@Test
	public void testIsAccepted()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		assertFalse(acc.isAccepted());
	}

	@Test
	public void testSetAccepted()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		acc.setAccepted();
		assertTrue(acc.isAccepted());
	}

	@Test
	public void testIsRated()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		assertFalse(acc.isRated());
	}

	@Test
	public void testSetRated()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		acc.setRated();
		assertTrue(acc.isRated());
	}

	@Test
	public void testSetMessageTo()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		acc.setMessageTo("bobo");
		assertEquals("bobo", acc.getMessageTo());
	}

	@Test
	public void testGetFrom()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		assertEquals("bibi", acc.getFrom());
	}

	@Test
	public void testGetTo()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		assertEquals("bobo", acc.getTo());
	}

	@Test
	public void testGetAnnonce()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		assertEquals("patate", acc.getAnnonce());
	}

	@Test
	public void testGetMessageFrom()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		assertEquals("baba", acc.getMessageFrom());
	}

	@Test
	public void testGetMessageTo()
	{
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		acc.setMessageTo("bobo");
		assertEquals("bobo", acc.getMessageTo());
	}

}
