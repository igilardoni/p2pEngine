/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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
	public void constructor() {
		Manager manager1 = new Manager(network);
		manager1.getUserManager().addUser(user1);
		manager1.getItemManager().addItem(item1_1);
		manager1.getItemManager().addItem(item1_2);
		System.out.println(user2.toString());
		manager1.getUserManager().addUser(user2);
		manager1.getItemManager().addItem(item2_2);
		
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
	public void delete(){
		Manager manager1 = new Manager(network);
		manager1.getUserManager().addUser(user1);
		manager1.getItemManager().addItem(item1_1);
		manager1.getItemManager().addItem(item1_2);
		manager1.getUserManager().addUser(user2);
		
		assertFalse(manager1.getUserManager().removeUserIfEmpty(user1));
		assertTrue(manager1.getUserManager().removeUserIfEmpty(user2));
		
		assertTrue(manager1.getUserManager().removeUser(user1));
	}
	
	/* TODO @Test */
	public void clearItems(){
		Manager manager1 = new Manager(network);
		manager1.getUserManager().addUser(user1);
		manager1.getUserManager().addUser(user2);
		manager1.getItemManager().addItem(item1_1);
		manager1.getItemManager().addItem(item1_2);
		manager1.getItemManager().addItem(item2_1);
		manager1.getItemManager().addItem(item2_2);
		

		Manager manager2 = new Manager(network);
		manager2.getUserManager().addUser(user1);
		manager2.getUserManager().addUser(user2);
		manager2.getItemManager().addItem(item1_1);
		manager2.getItemManager().addItem(item1_2);
		manager2.getItemManager().addItem(item2_1);
		
		manager1.getItemManager().cleanItems();
		
		assertTrue(manager1.toString().equals(manager2.toString()));
	}
	
	@AfterClass
	public static void close(){
		FileHelper fh = new FileHelper();
		fh.delete(new File(".peerFolderTest"));
	}
}
