package model.data.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Category.CATEGORY;
import model.data.manager.Manager;
import model.data.user.User;
import model.network.Network;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import util.FileHelper;

public class ManagerTest {
	private static User user1, user2;
	private static Item item1_1, item1_2;
	private static Item item2_1, item2_2;
	private static Network network;
	
	@BeforeClass
	public static void init(){
		user1 = new User("user1", "pwd1", "name1", "firstName1", "email1", "phone1");
		user1.sign(user1.getKeys());
		item1_1 = new Item(user1, "title11", new Category(CATEGORY.Baby), "description1", "", "country1", "contact1", 0, 1000000, Item.TYPE.OFFER);
		item1_1.sign(user1.getKeys());
		item1_2 = new Item(user1, "title12", new Category(CATEGORY.Appliances), "description2", "", "country2", "contact2", 0, 500000, Item.TYPE.OFFER);
		item1_2.sign(user1.getKeys());
		
		user2 = new User("user2", "pwd2", "name2", "firstName2", "email2", "phone2");
		user2.sign(user2.getKeys());
		item2_1 = new Item(user2, "title21", new Category(CATEGORY.Baby), "description1", "", "country1", "contact1", 0, 1000000, Item.TYPE.OFFER);
		item2_1.sign(user2.getKeys());
		item2_2 = new Item(user2, "title22", new Category(CATEGORY.Appliances), "description2", "", "country2", "contact2", 0, 1, Item.TYPE.OFFER);
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
		
		/* C'est normal que ça ne fonctionne pas...
		 * il faut que les users soient ajouté avant les items...
		 * ce n'est pas le cas puisque les items sortent avant les users !!! */
		/* Solution trouvée :
		 * Lors de l'ajout de setKeys, on peut rajouter un paramètre définissant l'order
		 * quand on créera l'arbre JDOM de sortie dans la méthode toString
		 * -> addContent(index, Child);
		 */
		// assertEquals(manager1.toString(), manager2.toString());
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
	
	@AfterClass
	public static void close(){
		FileHelper fh = new FileHelper();
		fh.delete(new File(".peerFolderTest"));
	}
}
