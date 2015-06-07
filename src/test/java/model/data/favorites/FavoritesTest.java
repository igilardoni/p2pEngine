package model.data.favorites;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import model.data.item.Category;
import model.data.item.Category.CATEGORY;
import model.data.item.Item;
import model.data.user.User;

import org.junit.BeforeClass;
import org.junit.Test;

import util.secure.Serpent;

public class FavoritesTest {
	private static User userOwnerItems;
	private static User userOwnerFavorites;
	private static String pwd;
	private static Item item1;
	private static Item item2;
	private static byte[] item1Crypted;
	private static byte[] item2Crypted;
	
	@BeforeClass
	public static void init(){
		userOwnerItems = new User("nick", "passWord", "name", "firstName", "email", "phone");
		userOwnerFavorites = new User("nick", "passWord", "name", "firstName", "email", "phone");
		pwd = userOwnerFavorites.getClearPwd();
		item1 = new Item(userOwnerItems, "title1", new Category(CATEGORY.NC), "description", "image", "country", "contact", 0L, 0L, Item.TYPE.PROPOSAL);
		item2 = new Item(userOwnerItems, "title2", new Category(CATEGORY.NC), "description", "image", "country", "contact", 0L, 0L, Item.TYPE.PROPOSAL);
		item1.sign(userOwnerItems.getKeys());
		item2.sign(userOwnerItems.getKeys());
		Serpent s = new Serpent(pwd);
		item1Crypted = s.encrypt(item1.toString().getBytes());
		item2Crypted = s.encrypt(item2.toString().getBytes());
	}
	
	@Test
	public void constructors(){
		Favorites f1 = new Favorites(userOwnerFavorites);
		f1.addItem(item1);
		f1.addItem(item2);
		Favorites f2 = new Favorites(userOwnerFavorites);
		f2.addItem(item1);
		f2.addItem(item2);
		Favorites f3 = new Favorites(f1.toString());
		
		assertEquals(f1,f2);
		assertEquals(f1.getItems(), f2.getItems());
		
		assertEquals(f1,f3);
		assertEquals(f1.getItems(), f3.getItems());
	}
	
	@Test
	public void cryptDecrypt(){
		Favorites f1 = new Favorites(userOwnerFavorites);
		f1.addItem(item1);
		f1.addItem(item2);
		Favorites f2 = new Favorites(f1.toString());
		f2.encrypt(pwd);
		f2.decrypt(pwd);
		
		assertEquals(f1.getItems(), f2.getItems());
		
		f2.encrypt(pwd);
		assertNull(f2.getItems());
		
		Favorites f3 = new Favorites(userOwnerFavorites);
		f3.addItemCrypted(item1Crypted);
		f3.addItemCrypted(item2Crypted);
		f3.decrypt(pwd);
		
		assertNotNull(f3.getItems());
		
		assertEquals(f1.getItems(), f3.getItems());
	}
	
	@Test
	public void cryptedConstructor(){
		Favorites f1 = new Favorites(userOwnerFavorites);
		f1.addItem(item1);
		f1.addItem(item2);
		f1.encrypt(pwd);
		Favorites f2 = new Favorites(f1.toString());
		
		f1.decrypt(pwd);
		f2.decrypt(pwd);
		
		assertEquals(f1.getItems(), f2.getItems());
	}
	
	@Test
	public void owner(){
		Favorites f1 = new Favorites((User) null);
		assertNull(f1.getOwner());
		
		f1.setOwner(userOwnerFavorites.getKeys().getPublicKey().toString(16));
		assertEquals(userOwnerFavorites.getKeys().getPublicKey().toString(16), f1.getOwner());
	}
}
