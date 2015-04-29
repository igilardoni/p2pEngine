package model.item;

import static org.junit.Assert.*;
import model.item.Category.CATEGORY;

import org.junit.Test;

public class ItemTest {
	private static String owner = "owner";
	private static String friendlyNick = "friendlyNick";
	private static String title = "title";
	private static Category category = new Category(CATEGORY.NC);
	private static String description = "description";
	private static String image = "";
	private static String country = "country";
	private static String contact = "contact";
	private static long lifeTime = 1;
	private static Item.TYPE type = Item.TYPE.PROPOSAL;
	
	@Test
	public void equals() {
		Item item1;
		Item item2;
		
		item1 = new Item(owner, friendlyNick, title, category, description, image, country, contact, 0, lifeTime, type);
		
		item2 = new Item();
		item2.setOwner(owner);
		item2.setTitle(title);
		item2.setCategory(category);
		item2.setDescription(description);
		item2.setImage(image);
		item2.setCountry(country);
		item2.setContact(contact);
		item2.setDate(0);
		item2.setLifeTime(lifeTime);
		item2.setType(type);
		
		assertTrue(item1.equals(item2)); // Equals expected
		
		item2.setOwner(owner+"!");
		
		assertFalse(item1.equals(item2)); // Different expected
	}
	
	@Test
	public void isAlive(){
		Item item;
		
		item = new Item(owner, friendlyNick, title, category, description, image, country, contact, 0, lifeTime, type);
		item.setDate(100000);
		assertFalse(item.isAlive());
		
		item.setDate(0);
		item.setLifeTime(100000);
		assertTrue(item.isAlive());
		
		item.setLifeTime(0);
		assertTrue(item.isAlive());
	}

}
