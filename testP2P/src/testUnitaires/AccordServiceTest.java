package testUnitaires;

import static org.junit.Assert.*;
import model.UsersManagement;
import model.communications.AccordService;

import org.junit.Test;

public class AccordServiceTest
{

	@Test
	public void testGetServiceName()
	{
		UsersManagement users = new UsersManagement();
		AccordService accServ = new AccordService(users);
		assertEquals("model.communications.AccordService", accServ.getServName());
	}

	@Test
	public void testGetServName()
	{
		UsersManagement users = new UsersManagement();
		AccordService accServ = new AccordService(users);
		assertEquals("model.communications.AccordService", accServ.getServName());
	}
}
