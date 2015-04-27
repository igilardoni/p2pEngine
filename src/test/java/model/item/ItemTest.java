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
	private static long date = System.currentTimeMillis();
	private static long lifeTime = 1;
	private static Item.TYPE type = Item.TYPE.PROPOSAL;
	
	@Test
	public void compareTo() {
		Item item1;
		Item item2;
		
		item1 = new Item(owner, friendlyNick, title, category, description, image, country, contact, date, lifeTime, type);
		
		item2 = new Item();
		item2.setOwner(owner);
		item2.setTitle(title);
		item2.setCategory(category);
		item2.setDescription(description);
		item2.setImage(image);
		item2.setCountry(country);
		item2.setContact(contact);
		item2.setDate(date);
		item2.setLifeTime(lifeTime);
		item2.setType(type);
		
		assertEquals(item1.compareTo(item2), 0); // Equals expected
		
		item2.setOwner(owner+"!");
		
		assertEquals(item1.compareTo(item2), 1); // Different expected
	}
	
	@Test
	public void isAlive(){
		Item item;
		
		item = new Item(owner, friendlyNick, title, category, description, image, country, contact, date, lifeTime, type);
		
		assertEquals(item.isAlive(), false);
		
		item.setLifeTime(Long.MAX_VALUE);
		
		assertEquals(item.isAlive(), true);
		
		item.setLifeTime(0);
		
		assertEquals(item.isAlive(), true);
	}

}
