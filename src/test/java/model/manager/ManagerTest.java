package model.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.item.Category;
import model.item.Category.CATEGORY;
import model.item.Item;
import model.network.Network;
import model.user.User;

import org.junit.BeforeClass;
import org.junit.Test;

public class ManagerTest {
	private static User user1, user2;
	private static Item item1_1, item1_2;
	private static Item item2_1, item2_2;
	private static Network network;
	
	@BeforeClass
	public static void init(){
		user1 = new User("user1", "pwd1", "name1", "firstName1", "email1", "phone1");
		user1.sign(user1.getKeys());
		item1_1 = new Item(user1, "title11", new Category(CATEGORY.Baby), "description1", "", "country1", "contact1", 0, 1000000, Item.TYPE.PROPOSAL);
		item1_1.sign(user1.getKeys());
		item1_2 = new Item(user1, "title12", new Category(CATEGORY.Appliances), "description2", "", "country2", "contact2", 0, 500000, Item.TYPE.PROPOSAL);
		item1_2.sign(user1.getKeys());
		
		user2 = new User("user2", "pwd2", "name2", "firstName2", "email2", "phone2");
		user2.sign(user2.getKeys());
		item2_1 = new Item(user2, "title21", new Category(CATEGORY.Baby), "description1", "", "country1", "contact1", 0, 1000000, Item.TYPE.PROPOSAL);
		item2_1.sign(user2.getKeys());
		item2_2 = new Item(user2, "title22", new Category(CATEGORY.Appliances), "description2", "", "country2", "contact2", 0, 1, Item.TYPE.PROPOSAL);
		item2_2.sign(user2.getKeys());
		
		network = new Network(9708, ".peerFolderTest", "peerNameTest");
		network.start();
	}
	
	@Test
	public void getItem(){
		Manager manager = new Manager(network);
		manager.addUser(user1);
		manager.addItem(item1_1);
		manager.addItem(item1_2);
		
		assertEquals(item1_1, manager.getItem(user1.getKeys().getPublicKey().toString(16), item1_1.getTitle()));
		
		manager.login("user1", "pwd1");
		
		assertEquals(item1_1, manager.getItemCurrentUser(item1_1.getTitle()));
	}
	
	@Test
	public void constructor() {
		Manager manager1 = new Manager(network);
		manager1.addUser(user1);
		manager1.addItem(item1_1);
		manager1.addItem(item1_2);
		System.out.println(user2.toString());
		manager1.addUser(user2);
		manager1.addItem(item2_2);
		
		Manager manager2 = new Manager(manager1.toString(), network);
		
		assertTrue(manager1.toString().equals(manager2.toString()));
	}
	
	@Test
	public void messageEvent(){
		Manager manager1 = new Manager(network);
		manager1.addUser(user1);
		manager1.addItem(item1_1);
		manager1.addItem(item1_2);
		
		Manager manager2 = new Manager(null);
		
		manager2.messageEvent(manager1);
		
		assertTrue(manager1.toString().equals(manager2.toString()));
	}
	
	@Test
	public void delete(){
		Manager manager1 = new Manager(network);
		manager1.addUser(user1);
		manager1.addItem(item1_1);
		manager1.addItem(item1_2);
		manager1.addUser(user2);
		
		assertFalse(manager1.removeUserIfEmpty(user1));
		assertTrue(manager1.removeUserIfEmpty(user2));
		
		assertTrue(manager1.removeUser(user1));
	}
	
	@Test
	public void clearItems(){
		Manager manager1 = new Manager(network);
		manager1.addUser(user1);
		manager1.addUser(user2);
		manager1.addItem(item1_1);
		manager1.addItem(item1_2);
		manager1.addItem(item2_1);
		manager1.addItem(item2_2);
		

		Manager manager2 = new Manager(network);
		manager2.addUser(user1);
		manager2.addUser(user2);
		manager2.addItem(item1_1);
		manager2.addItem(item1_2);
		manager2.addItem(item2_1);
		
		manager1.cleanItems();
		
		assertTrue(manager1.toString().equals(manager2.toString()));
	}
	
	@Test
	public void login(){
		// TODO
	}
}
