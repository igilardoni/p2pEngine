package model.manager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.jxta.endpoint.Message;
import model.item.Item;
import model.network.communication.service.ServiceListener;
import model.user.User;

public class Manager implements ServiceListener<User> {
	HashMap<Item, String> association = new HashMap<Item, String>();
	HashMap<String, User> users = new HashMap<String, User>();
	ArrayList<Item> items = new ArrayList<Item>();
	
	/**
	 * Add a user to the manager
	 * @param user
	 * @return true if the user can be added, false if the user is already contained
	 */
	public boolean addUser(User user){
		if(users.containsValue(user))
			return false;
		users.put(user.getKey().getPublicKey().toString(16),user);
		return true;
	}
	
	/**
	 * Add an item to the manager
	 * @param item
	 * @return true if the item can be added, false if the owner isn't in the Manager or if item don't contain an owner
	 */
	public boolean addItem(Item item){
		if(item == null)
			return false;
		String owner = item.getOwner();
		if(owner.isEmpty())
			return false;
		if(!users.containsKey(owner))
			return false;
		if(items.contains(item))
			return false;
		items.add(item);
		association.put(item, owner);
		return true;
	}
	
	/**
	 * Remove an user if he haven't item !
	 * @param user
	 * @return
	 */
	public boolean removeUserIfNotItem(User user){
		String userKey = user.getPublicKey().toString(16);
		if(!users.containsKey(user.getPublicKey().toString(16)))
			return false;
		for (Entry<Item, String> i : association.entrySet()) {
			if(i.getValue().equals(userKey))
				return false;
		}
		users.remove(userKey);
		return true;
	}
	
	/**
	 * Remove an user and items of him.
	 * @param user
	 * @return
	 */
	public boolean removeUserWithItems(User user){
		String userKey = user.getPublicKey().toString(16);
		if(!users.containsKey(user.getPublicKey().toString(16)))
			return false;
		boolean valid = true;
		for (Entry<Item, String> i : association.entrySet()) {
			if(i.getValue().equals(userKey))
				valid &= (association.remove(i.getKey())!=null);
		}
		return (valid &= (users.remove(userKey)!=null));
	}
	
	/**
	 * Remove an item from the Manager
	 * @param item
	 * @return
	 */
	public boolean removeItem(Item item){
		return items.remove(item) && association.remove(item)!=null;
	}
	
	/**
	 * Return the user with this key
	 * @param key - String format
	 * @return
	 */
	public User whoIs(String key){
		return users.get(key);
	}
	
	/**
	 * Return the user with this key
	 * @param key - BigInteger format
	 * @return
	 */
	public User whoIs(BigInteger key){
		return users.get(key.toString(16));
	}
	
	/**
	 * Return user who has this item
	 * @param item
	 * @return
	 */
	public User whoHas(Item item){
		return users.get(association.get(item));
	}
	
	/**
	 * Return the public Key of the owner
	 * @param item
	 * @return
	 */
	public String whatPublicHas(Item item){
		return association.get(item);
	}

	@Override
	public void messageEvent(User m) {
		addUser(m);
	}
}
