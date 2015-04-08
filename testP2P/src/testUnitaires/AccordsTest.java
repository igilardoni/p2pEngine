package testUnitaires;

import static org.junit.Assert.*;
import model.Accord;
import model.Accords;

import org.junit.Test;

public class AccordsTest
{
	
	
	@Test
	public void testAcceptAccord()
	{
		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		listeAcc.acceptAccord(acc);
		assertTrue(acc.isAccepted());
	}

	@Test
	public void testRateAccord()
	{

		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		listeAcc.rateAccord(acc);
		assertTrue(acc.isRated());
	}

	@Test
	public void testGetAccord()
	{
		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		Accord acc1 = new Accord("lili", "lolo", "lala", "patate");
		listeAcc.addAccord(acc1);
		listeAcc.addAccord(acc);
		assertEquals(acc1, listeAcc.getAccord("lili", "lolo", "patate"));
	}

	@Test
	public void testDeclineAccord()
	{
		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");
		Accord acc1 = new Accord("lili", "lolo", "lala", "patate");
		listeAcc.addAccord(acc1);
		listeAcc.addAccord(acc);
		listeAcc.declineAccord("bibi", "bobo", "patate");
		assertEquals(null,listeAcc.getAccord("bibi", "bobo", "patate"));
	}
	
	@Test
	public void testAddAccord()
	{
		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate");

		listeAcc.addAccord(acc);
		assertEquals(acc, listeAcc.getAccord("bibi", "bobo", "patate"));
	}


}
