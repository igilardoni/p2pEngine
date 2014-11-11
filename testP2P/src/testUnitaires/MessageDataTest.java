package testUnitaires;

import static org.junit.Assert.*;
import model.communications.MessageData;

import org.junit.Test;

public class MessageDataTest
{

	@Test
	public void testGetContent()
	{
		MessageData msg = new MessageData("bibi","bobo","patates",0);
		assertEquals("patates",msg.getContent());
	}

	@Test
	public void testGetTo()
	{
		MessageData msg = new MessageData("bibi","bobo","patates",0);
		assertEquals("bobo",msg.getTo());
	}

	@Test
	public void testGetDate()
	{
		MessageData msg = new MessageData("bibi","bobo","patates",0);
		assertEquals(0,msg.getDate());
	}

	@Test
	public void testGetSender()
	{
		MessageData msg = new MessageData("bibi","bobo","patates",0);
		assertEquals("bibi",msg.getSender());
	}

}
