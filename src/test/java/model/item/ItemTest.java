package model.item;

import static org.junit.Assert.*;
import model.item.Category.CATEGORY;

import org.junit.Test;

public class ItemTest {

	@Test
	public void compareTo() {
		Item item1;
		Item item2;
		
		String owner = "Moi";
		String title = "item";
		Category category = new Category(CATEGORY.NC);
		String description = "Aucune description";
		String image = "";
		String country = "FRANCE";
		String contact = "Ne m'appelez pas";
		long date = System.currentTimeMillis();
		long lifeTime = 1000000;
		Item.TYPE type = Item.TYPE.PROPOSAL;
		
		item1 = new Item(owner, title, category, description, image, country, contact, date, lifeTime, type);
		
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
		
		assertEquals(item1.compareTo(item2), 0);
		
		item2.setOwner(owner+"!");
		
		assertEquals(item1.compareTo(item2), 1);
	}
	
	@Test
	public void isAlive(){
		Item item;
		
		String owner = "Moi";
		String title = "item";
		Category category = new Category(CATEGORY.NC);
		String description = "Aucune description";
		String image = "";
		String country = "FRANCE";
		String contact = "Ne m'appelez pas";
		long date = System.currentTimeMillis()-1;
		long lifeTime = 0;
		Item.TYPE type = Item.TYPE.PROPOSAL;
		
		item = new Item(owner, title, category, description, image, country, contact, date, lifeTime, type);
		
		assertEquals(item.isAlive(), false);
		
		item.setLifeTime(10000);
		
		assertEquals(item.isAlive(), true);
	}

}
