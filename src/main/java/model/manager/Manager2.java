package model.manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.jdom2.Element;

import util.StringToElement;
import model.advertisement.AbstractAdvertisement;
import model.item.Category;
import model.item.Category.CATEGORY;
import model.item.Item;
import model.user.User;

public class Manager2 extends AbstractAdvertisement {
	private HashMap<String, User> users; //The string key is the user's public key in hexadecimal
	private ArrayList<Item> items;
	
	public Manager2(String XML) {
		super(XML);
	}

	public Manager2() {
		super();
	}

	public void addUser(User u) {
		users.put(u.getPublicKey().toString(16), u);
	}
	
	public void addItem(Item i) {
		items.add(i);
	}
	
	@Override
	protected String getAdvertisementName() {
		return Manager2.class.getSimpleName();
	}

	@Override
	protected void setKeys() {
		users = new HashMap<String, User>();
		items = new ArrayList<Item>();
		addKey("users", false);
		addKey("items", false);
	}

	/**
	 * Get an XML string representing all the users that are saved on this device.
	 * @return A string, XML formated
	 */
	private String getUsersXML() {
		StringBuffer s = new StringBuffer();
		for(User u: users.values()) {
			s.append(u); 
		}
		return s.toString();
	}
	
	/**
	 * Get an XML string representing all the users that are saved on this device.
	 * @return A string, XML formated
	 */
	private String getItemsXML() {
		StringBuffer s = new StringBuffer();
		for(Item i: items) {
			s.append(i); 
		}
		return s.toString();
	}
	
	@Override
	protected void putValues() {
		addValue("users", getUsersXML());
		addValue("items", getItemsXML());
	}
	
	/**
	 * Load all the users in this element
	 * @param e an element that contains users in XML format.
	 */
	private void loadUsers(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element u: root.getChildren()) {
			addUser(new User(u));
		}
	}
	
	private void loadItems(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element i: root.getChildren()) {
			addItem(new Item(i));
		}
	}
	
	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "users": loadUsers(e); break;
		case "items": loadItems(e); break;
		default: return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		Manager2 manager = new Manager2();
		User user1 = new User("user1", "pass2", "name1", "firstname1", "email1", "phone1");
		User user2 = new User("user2", "pass2", "name2", "firstname2", "email2", "phone2");
		Item item1 = new Item(user1, "patate", new Category(Category.CATEGORY.Appliances), 
				"osef", null, "france", "???", 145L, 1000L, Item.TYPE.WISH);
		Item item2 = new Item(user2, "carotte", new Category(Category.CATEGORY.Appliances), 
				"osef", null, "france", "???", 145L, 1000L, Item.TYPE.WISH);
		manager.addUser(user1);
		manager.addUser(user2);
		manager.addItem(item1);
		manager.addItem(item2);
		
		Manager2 manager2 = new Manager2(manager.toString());
		if(manager2.toString().equals(manager.toString())) {
			System.out.println("ok !");
		}
	}
	
}


