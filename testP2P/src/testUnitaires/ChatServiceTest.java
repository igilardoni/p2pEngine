package testUnitaires;	

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import model.UsersManagement;
import model.communications.ChatService;

import org.junit.Test;

public class ChatServiceTest
{

	@Test
	public void testGetServiceName()
	{
		UsersManagement users = new UsersManagement();
		ChatService chat = new ChatService(users);
		assertEquals("model.communications.ChatService", chat.getServName());
	}

	@Test
	public void testGetServName()
	{
		UsersManagement users = new UsersManagement();
		ChatService chat = new ChatService(users);
		assertEquals("model.communications.ChatService", chat.getServName());
	}

}
