package testUnitaires;

import static org.junit.Assert.*;
import model.communications.Accord;
import model.communications.Accords;

import org.junit.Test;

public class AccordsTest
{
	
	
	@Test
	public void testAcceptAccord()
	{
		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		listeAcc.acceptAccord(acc);
		assertTrue(acc.isAccepted());
	}

	@Test
	public void testRateAccord()
	{

		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		listeAcc.rateAccord(acc);
		assertTrue(acc.isRated());
	}

	@Test
	public void testGetAccord()
	{
		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		Accord acc1 = new Accord("lili", "lolo", "lala", "patate", null);
		listeAcc.addAccord(acc1);
		listeAcc.addAccord(acc);
		assertEquals(acc1, listeAcc.getAccord("lili", "lolo", "patate"));
	}

	@Test
	public void testDeclineAccord()
	{
		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);
		Accord acc1 = new Accord("lili", "lolo", "lala", "patate", null);
		listeAcc.addAccord(acc1);
		listeAcc.addAccord(acc);
		listeAcc.declineAccord("bibi", "bobo", "patate");
		assertEquals(null,listeAcc.getAccord("bibi", "bobo", "patate"));
	}
	
	@Test
	public void testAddAccord()
	{
		Accords listeAcc = new Accords();
		Accord acc = new Accord("bibi", "bobo", "baba", "patate", null);

		listeAcc.addAccord(acc);
		assertEquals(acc, listeAcc.getAccord("bibi", "bobo", "patate"));
	}


}
