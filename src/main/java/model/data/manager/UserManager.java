package model.data.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.jxta.discovery.DiscoveryService;

import org.jdom2.Element;

import util.Printer;
import util.StringToElement;
import util.VARIABLES;
import util.secure.AsymKeysImpl;
import model.data.contrat.Contrat;
import model.data.favorites.Favorites;
import model.data.item.Item;
import model.data.user.Conversations;
import model.data.user.User;
import model.data.user.UserMessage;
import model.network.search.Search;

public class UserManager {
	private HashMap<String, User> users = new HashMap<String,User>();  // The string key is the user's public key in hexadecimal
	private User currentUser = null;				                   // User logged
	private Manager manager;
	
	
	///////////////////////////////////////////////// CONSTRUCTORS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public UserManager(Manager m) {
		manager = m;
	}
	
	
	///////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Return the user with this publicKey
	 * @param publicKey - String format
	 * @return
	 */
	public User getUser(String publicKey){
		return users.get(publicKey);
	}
	
	/**
	 * Get the current User. If no user logged, return null.
	 * @return
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	
	/**
	 * Get the entire list of users
	 * @return a Collection of User
	 */
	public Collection<User> getUsers() {
		return users.values();
	}
	
	/**
	 * Return user who has this item
	 * @param item
	 * @return
	 */
	public User getItemUser(Item item){
		return users.get(item.getOwner());
	}
	
	/**
	 * Return an Array List which contain all User named nickName (parameter)
	 * TODO ca retourne pas un array list :p
	 * @param nickName
	 * @return
	 */
	public User getNamed(String nickName){
		for (User user : this.users.values()) {
			if(user.getNick().equals(nickName))
				return user;
		}
		return null;
	}
	
	/**
	 * Return the user's items' list
	 * @param publicKey the user public key
	 * @return a new list containing user's items
	 */
	public ArrayList<Item> getUserItems(String publicKey) {
		ArrayList<Item> userItems = new ArrayList<Item>();
		for(Item i: manager.getItemManager().getItems()) {
			if(i.getOwner().equals(publicKey)) {
				userItems.add(i);
			}
		}
		return userItems;
	}
	
	
	///////////////////////////////////////////////// ADDERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * to add an user in this instance of manager
	 * if user is already in the manager, this function remove old and put User u
	 * @param u - User to add
	 */
	public void addUser(User u){
		if(u == null){
			Printer.printError(this, "addUser","This User is empty !");
			return;
		}
		if(!u.checkSignature(u.getKeys())){
			Printer.printError(this, "addUser","Bad Signature for "+u.getNick());
			return;
		}
		String key = u.getKeys().getPublicKey().toString(16);
		if(users.containsKey(key)){
			if(users.get(key).equals(u) && users.get(key).getLastUpdated() >= u.getLastUpdated()){
				Printer.printError(this, "addUser","User "+u.getNick()+" is already registred !");
				return;
			}
		}
		users.put(key, u);
		manager.getDealManager().getDeals().put(key, new ArrayList<Contrat>());
	}
	
	/**
	 * Registry an user in the manager
	 * @param user
	 * @return
	 */
	public void registration(User user){
		if(user == null){
			System.err.println(Manager.class.getName()+".registration : can't register null user");
			return;
		}
		
		user.getKeys().decryptPrivateKey(user.getClearPwd());
		
		if(user.getKeys() == null || !user.getKeys().isCompatible()){
			System.err.println(Manager.class.getName()+".registration : can't register user without compatible keys !");
			return;
		}
		
		AsymKeysImpl originalKey = user.getKeys().copy();
		user.getKeys().encryptPrivateKey(user.getClearPwd());
		user.sign(originalKey);
		this.addUser(user);
	}	
	
	/**
	 * Add an user
	 * @param u the user. Must me signed.
	 * @param publish true if the user is emmediatly publish on the network.
	 */
	public void addUser(User u, boolean publish) {
		addUser(u);
		if(publish) {
			try {
				manager.getNetwork().getGroup("users").getDiscoveryService().publish(u);
			} catch (IOException e) {
				Printer.printError(this, "addUser",e.toString());
			}
		}
	}
	
	///////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/**
	 * Get an XML string representing all the users that are saved on this device.
	 * @return A string, XML formated
	 */
	protected String getUsersXML() {
		StringBuffer s = new StringBuffer();
		for(User u: users.values()) {
			s.append(u);
		}
		return s.toString();
	}
	
	/**
	 * Load all the users in this element
	 * @param e an element that contains users in XML format.
	 * TODO a voir
	 */
	protected void loadUsers(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element u: root.getChildren()) {
			addUser(new User(u));
		}
	}

	///////////////////////////////////////////////// REMOVERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Remove an user if he haven't item and message !
	 * @param user
	 * @return
	 */
	public boolean removeUserIfEmpty(User user){
		String userKey = user.getKeys().getPublicKey().toString(16);
		if(getUser(userKey) == null)
			return false;
		for (Item i : manager.getItemManager().getItems()) {
			if(i.getOwner().equals(userKey))
				return false;
		}
		return users.remove(userKey)!=null;
	}
	
	/**
	 * Remove an user.
	 * If this user have items, they will be deleted.
	 * If this user have messages, they will be deleted.
	 * @param user
	 * @return
	 */
	public boolean removeUser(User user){
		String userKey = user.getKeys().getPublicKey().toString(16);
		if(!users.containsKey(user.getKeys().getPublicKey().toString(16)))
			return false;
		boolean valid = true;
		for (Item i : manager.getItemManager().getItems()) {
			if(i.getOwner().equals(userKey))
				valid &= manager.getItemManager().getItems().remove(i);
		}
		
		return (valid &= (users.remove(userKey)!=null));
	}
	
	
	///////////////////////////////////////////////// UTILS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Log out the current User.
	 */
	public void logout() {
		AsymKeysImpl clearKeys = currentUser.getKeys().copy();
		clearKeys.decryptPrivateKey(currentUser.getClearPwd());
		String clearPassword = new String(currentUser.getClearPwd());
		/*if(manager.getFavoriteManager().getFavoritesCurrentUser() != null){
			manager.getFavoriteManager().getFavoritesCurrentUser().encrypt(clearPassword);
			manager.getFavoriteManager().getFavoritesCurrentUser().sign(currentUser.getKeys());
		}*/
		currentUser.getKeys().encryptPrivateKey(clearPassword);
		currentUser.sign(clearKeys);
		manager.saving(VARIABLES.ManagerFilePath);
		currentUser.setClearPassword(null);
		currentUser = null;
	}
	
	public boolean userExists(String key) {
		return users.containsKey(key);
	}
	
	/**
	 * Retrieve the corresponding user according to nickname and password.
	 * @param nickname
	 * @param password
	 */
	public boolean login(String nickname, String password) {
		// TODO This method have to change if nickName can be same on different account !
		User u = null;
		if(users.size()>0)
			u = this.getNamed(nickname);
		// Search on network
		Search<User> search = new Search<User>(manager.getNetwork().getGroup("users").getDiscoveryService(), "nick", true);
		search.search(nickname, VARIABLES.CheckTimeAccount, VARIABLES.ReplicationsAccount);
		ArrayList<User> results = search.getResults();
		if(results.isEmpty() && u==null){
			Printer.printError(this, "login", "Account not found !");
			return false;
		}
		long maxUpdate = 0;
		if(u != null)
			maxUpdate = u.getLastUpdated();
		for (User user : results) {
			if(user.checkSignature(user.getKeys())){
				results.remove(user);
				continue;
			}
			maxUpdate = user.getLastUpdated() > maxUpdate ? user.getLastUpdated() : maxUpdate;
		}
		for(User user : results){
			if(user.getLastUpdated() == maxUpdate)
				u = user;
			else
				results.remove(user);
		}
		if(this.getNamed(nickname) != null)
			this.addUser(u);
		// Check password
		if(!u.isPassword(password))
			return false;
		// Check privateKey decryption
		if(!u.getKeys().decryptPrivateKey(password))
			return false;
		currentUser = u;
		currentUser.setClearPassword(password);
		manager.getFavoriteManager().getFavoritesCurrentUser().decrypt(password);
		return currentUser != null;
	}
	
	///////////////////////////////////////////////// PUBLISHERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	protected void publishUsers() {
		DiscoveryService discovery = manager.getNetwork().getGroup("users").getDiscoveryService();
		for(User u: users.values()) {
			try {
				discovery.flushAdvertisement(u);
				discovery.publish(u); //"i have this user"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
