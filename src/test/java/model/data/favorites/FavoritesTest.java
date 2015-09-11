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
package model.data.favorites;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import model.data.item.Category;
import model.data.item.Category.CATEGORY;
import model.data.item.Item;
import model.data.user.User;

import org.junit.BeforeClass;
import org.junit.Test;

import util.Hexa;
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
		item1 = new Item(userOwnerItems, "title1", new Category(CATEGORY.NA), "description", "image", "country", "contact", 0L, 0L, Item.TYPE.OFFER);
		item2 = new Item(userOwnerItems, "title2", new Category(CATEGORY.NA), "description", "image", "country", "contact", 0L, 0L, Item.TYPE.OFFER);
		item1.sign(userOwnerItems.getKeys());
		item2.sign(userOwnerItems.getKeys());
		Serpent s = new Serpent(pwd);
		item1Crypted = s.encrypt(item1.getItemKey().getBytes());
		item2Crypted = s.encrypt(item2.getItemKey().getBytes());
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
		assertEquals(f1.getItemsKey(), f2.getItemsKey());
		
		assertEquals(f1,f3);
		assertEquals(f1.getItemsKey(), f3.getItemsKey());
	}
	
	@Test
	public void constructorsCrypted(){
		Favorites f1 = new Favorites(userOwnerFavorites);
		f1.addItem(item1);
		f1.addItem(item2);
		f1.encrypt(pwd);
		Favorites f2 = new Favorites(f1.toString());
		
		assertEquals(f1, f2);
		
		f1.decrypt(pwd);
		f2.decrypt(pwd);
		
		assertEquals(f1, f2);
	}
	
	@Test
	public void cryptDecrypt(){
		Favorites f1 = new Favorites(userOwnerFavorites);
		f1.addItem(item1);
		f1.addItem(item2);
		Favorites f2 = new Favorites(f1.toString());
		f2.encrypt(pwd);
		f2.decrypt(pwd);
		
		assertEquals(f1.getItemsKey(), f2.getItemsKey());
		
		f2.encrypt(pwd);
		
		Favorites f3 = new Favorites(userOwnerFavorites);
		f3.setCrypted(false);
		f3.addItem(Hexa.bytesToHex(item1Crypted));
		f3.addItem(Hexa.bytesToHex(item2Crypted));
		f3.setCrypted(true);
		
		assertEquals(f2.getItemsKey(), f3.getItemsKey());
		
		f3.decrypt(pwd);
		
		assertEquals(f1.getItemsKey(), f3.getItemsKey());
	}
	
	@Test
	public void owner(){
		Favorites f1 = new Favorites((User) null);
		assertNull(f1.getOwner());
		
		f1.setKeys(userOwnerFavorites.getKeys());
		assertEquals(userOwnerFavorites.getKeys().getPublicKey().toString(16), f1.getOwner());
	}
}
