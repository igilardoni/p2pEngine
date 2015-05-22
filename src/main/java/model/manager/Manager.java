package model.manager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import model.advertisement.AbstractAdvertisement;
import model.item.Item;
import model.network.NetworkInterface;
import model.network.communication.service.ServiceListener;
import model.network.search.Search;
import model.user.Conversations;
import model.user.Message;
import model.user.User;
import net.jxta.discovery.DiscoveryService;

import org.jdom2.Element;

import util.StringToElement;
import util.VARIABLE;
import util.secure.AsymKeysImpl;
import model.network.Network;

/**
 * Local manager for Users, items and messages.
 * @author Julien Prudhomme
 * @author Michael Dubuis
 *
 */
public class Manager extends AbstractAdvertisement implements ServiceListener<Manager> {
	
	private HashMap<String, User> users;		// The string key is the user's public key in hexadecimal
	private ArrayList<Item> items;				// list of items handled by this manager.
	private NetworkInterface network;
	private User currentUser;					// User logged
	private ArrayList<Message> messages;		// Messages for users attempting to be received.
	private HashMap<String, Conversations> conversations; //users's conversation (already received.) (string : user public key that own the conversations
	
	/**
	 * Create a manager based on a String that is XML formated.
	 * @param XML
	 */
	public Manager(String XML, NetworkInterface network) {
		super(XML);
		this.network = network;
	}

	public Manager(NetworkInterface network) {
		super();
		this.network = network;
	}
	
	/**
	 * to add an user in this instance of manager
	 * if user is already in the manager, this function remove old and put User u
	 * @param u - User to add
	 */
	public void addUser(User u){
		if(u == null){
			System.err.println(this.getAdvertisementName()+" : This User is empty !");
			return;
		}
		if(!u.checkSignature(u.getKeys())){
			System.err.println(this.getAdvertisementName()+" : Bad Signature for "+u.getNick());
			return;
		}
		String key = u.getKeys().getPublicKey().toString(16);
		if(users.containsKey(key)){
			if(users.get(key).equals(u) && users.get(key).getLastUpdated() >= u.getLastUpdated()){
				System.err.println(this.getAdvertisementName()+" : User "+u.getNick()+" is already registred !");
				return;
			}
		}
		users.put(key, u);
	}
	
	/**
	 * to add a item in this instance of manager
	 * if owner of the item isn't registered in this instance of manger, function will fail
	 * @param i - Item to add
	 */
	public void addItem(Item i){
		if(i == null){
			System.err.println(this.getAdvertisementName()+" : This Item is empty !");
			return;
		}
		String owner = i.getOwner();
		if(owner.isEmpty()){
			System.err.println(this.getAdvertisementName()+" : No owner found !");
			return;
		}
		if(!users.containsKey(owner)){
			System.err.println(this.getAdvertisementName()+" : Owner unknown for "+i.getTitle());
			return;
		}
		if(!i.checkSignature(users.get(owner).getKeys())){
			System.err.println(this.getAdvertisementName()+" : Bad Signature for "+i.getTitle());
			return;
		}
		if(items.contains(i)){
			if(items.get(items.indexOf(i)).getLastUpdated() >= i.getLastUpdated()){
				System.err.println(this.getAdvertisementName()+" : Item "+i.getTitle()+" is already registred !");
				return;
			}
		}
		items.add(i);
	}
	
	/**
	 * to add a message
	 * if receiver of the message isn't registered in this instance of manger, function will fail
	 * @param m
	 */
	public void addMessage(Message m){
		if(m == null){
			System.err.println(this.getAdvertisementName()+" : This Message is null !");
			return;
		}
		String owner = m.getOwner();
		if(owner.isEmpty()){
			System.err.println(this.getAdvertisementName()+" : No owner found !");
			return;
		}
		if(!users.containsKey(owner)){
			System.err.println(this.getAdvertisementName()+" : Owner unknown "+owner);
			return;
		}
		if(!m.checkSignature(users.get(owner).getKeys())){
			System.err.println(this.getAdvertisementName()+" : Bad Signature");
			return;
		}
		if(messages.contains(m)){
			if(messages.get(messages.indexOf(m)).getLastUpdated() >= m.getLastUpdated()){
				System.err.println(this.getAdvertisementName()+" : This message is already registred !");
				return;
			}
		}
		messages.add(m);
	}

	////////////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Return an XML string containing user's info and his items and his messages.
	 * @param publicKey
	 * @return a string, XML-formated, containing the user and his objects and his messages
	 */
	public String completUserXMLString(String publicKey) {
		StringBuffer s = new StringBuffer();
		s.append(this.getUser(publicKey).toString());
		s.append("<Items>");
		for(Item i : getUserItems(publicKey)) {
			s.append(i.toString());
		}
		s.append("</Items>");
		s.append("<Messages>");
		for(Message m : getUserMessages(publicKey)){
			s.append(m.toString());
		}
		s.append("</Messages>");
		s.append(conversations.get(publicKey).toString());
		return s.toString();
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
	 * Get an XML string representing all the items that are saved on this device.
	 * @return A string, XML formated
	 */
	private String getItemsXML() {
		StringBuffer s = new StringBuffer();
		for(Item i: items) {
			s.append(i); 
		}
		return s.toString();
	}
	
	/**
	 * Get an XML string representing all the messages that are saved on this device.
	 * @return A string, XML formated
	 */
	private String getMessagesXML() {
		StringBuffer s = new StringBuffer();
		for(Message m: messages) {
			s.append(m); 
		}
		return s.toString();
	}
	
	private String getReceivedMessagesXML() {
		StringBuffer s = new StringBuffer();
		for(Conversations c : conversations.values()) {
			s.append(c);
		}
		return s.toString();
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
	
	/**
	 * Load all the items in this element
	 * @param e an element that contains items in XML format.
	 */
	private void loadItems(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element i: root.getChildren()) {
			addItem(new Item(i));
		}
	}
	
	/**
	 * Load all the messages in this element
	 * @param e an element that contains messages in XML format.
	 */
	private void loadMessages(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element m: root.getChildren()) {
			addMessage(new Message(m));
		}
	}

	/**
	 * Load all the messages in this element
	 * @param e an element that contains messages in XML format.
	 */
	private void loadReceivedMessages(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element m: root.getChildren()) {
			addConversations(new Conversations(m));
		}
	}
	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "users": 				loadUsers(e); break;
		case "items": 				loadItems(e); break;
		case "messages": 			loadMessages(e); break;
		case "ReceivedMessages":	loadReceivedMessages(e); break;
		default: return false;
		}
		return true;
	}

	@Override
	protected String getAdvertisementName() {
		return Manager.class.getSimpleName();
	}

	@Override
	protected void setKeys() {
		users = new HashMap<String, User>();
		items = new ArrayList<Item>();
		messages = new ArrayList<Message>();
		conversations = new HashMap<String, Conversations>();
		currentUser = null;
		addKey("users", false);
		addKey("items", false);
		addKey("messages", false);
		addKey("ReceivedMessages", false);
	}
	
	@Override
	protected void putValues() {
		addValue("users", getUsersXML());
		addValue("items", getItemsXML());
		addValue("messages", getMessagesXML());
		addValue("ReceivedMessages", getReceivedMessagesXML());
	}

	/////////////////////////////////////////////// SERVICE LISTENER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	public void messageEvent(Manager m) {
		// TODO Tests
        Element elements;
    	// Add all Users
		elements = null;
    	elements  = StringToElement.getElementFromString(m.getUsersXML(), "users");
    	for (Element element : elements.getChildren()) {
			User user = new User(element);
			if(user.checkSignature(user.getKeys()))
				this.addUser(user);
		}
		// Add all Items
		elements = null;
		elements  = StringToElement.getElementFromString(m.getItemsXML(), "items");
		for (Element element : elements.getChildren()) {
			Item item = new Item(element);
			if(item.checkSignature(this.getUser(item.getOwner()).getKeys()))
				this.addItem(item);
		}
		// Add all Messages
		elements = null;
		elements  = StringToElement.getElementFromString(m.getMessagesXML(), "messages");
		for (Element element : elements.getChildren()) {
			Message message = new Message(element);
			// PAS DE TEST POSSIBLE SI ON EST PAS LE PROPRIETAIRE DU MESSAGE !
			this.addMessage(message);
		}
		// Add all Conversations
		elements = null;
		elements  = StringToElement.getElementFromString(m.getReceivedMessagesXML(), "ReceivedMessages");
		for (Element element : elements.getChildren()) {
			Conversations conversations = new Conversations(element);
			// TODO TEST !
			this.addConversations(conversations);
		}
	}
	
	////////////////////////////////////////////////////// UTIL \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Remove an user if he haven't item and message !
	 * @param user
	 * @return
	 */
	public boolean removeUserIfEmpty(User user){
		String userKey = user.getKeys().getPublicKey().toString(16);
		if(!users.containsKey(userKey))
			return false;
		for (Item i : items) {
			if(i.getOwner().equals(userKey))
				return false;
		}
		for (Message m : messages) {
			if(m.getOwner().equals(userKey))
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
		for (Item i : items) {
			if(i.getOwner().equals(userKey))
				valid &= items.remove(i);
		}
		for(Message m : messages){
			if(m.getOwner().equals(user.getKeys().getPublicKey()))
				valid &= messages.remove(m);
		}
		return (valid &= (users.remove(userKey)!=null));
	}
	
	/**
	 * Remove an item from the Manager
	 * @param item
	 * @return
	 */
	public boolean removeItem(Item item){
		return items.remove(item);
	}
	
	/**
	 * to remove all items with lifeTime is over
	 */
	public void cleanItems(){
		for(int i = 0; i <items.size();i++){
			if(!items.get(i).isAlive(whoHas(items.get(i)).getLastUpdated()
					))
				removeItem(items.get(i));
		}
	}
	
	/**
	 * Remove a message from the Manager
	 * @param msg
	 * @return
	 */
	public boolean removeMessage(Message msg){
		return messages.remove(msg);
	}
	
	///////////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Collection<User> getUsers() {
		return users.values();
	}
	
	/**
	 * Return the user with this key
	 * @param key - String format
	 * @return
	 */
	public User getUser(String key){
		return users.get(key);
	}
	
	/**
	 * Return the user with this key
	 * @param key - BigInteger format
	 * @return
	 */
	public User getUser(BigInteger key){
		return users.get(key.toString(16));
	}
	
	/**
	 * Return user who has this item
	 * @param item
	 * @return
	 */
	public User whoHas(Item item){
		return users.get(item.getOwner());
	}

	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Return an Array List which contain all User named nickName (parameter)
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
		for(Item i: items) {
			if(i.getOwner().equals(publicKey)) {
				userItems.add(i);
			}
		}
		return userItems;
	}
	
	/**
	 * Use to found a item with owner's publicKey and item's title
	 * @param publicKey
	 * @param title
	 * @return
	 */
	public Item getItem(String publicKey, String title){
		if(publicKey == null || publicKey.isEmpty()){
			System.err.println(this.getAdvertisementName()+".getItem : publicKey is empty or null !");
			return null;
		}
		if(title == null || title.isEmpty()){
			System.err.println(this.getAdvertisementName()+".getItem : title is empty or null !");
			return null;
		}
		if(!users.containsKey(publicKey)){
			System.err.println(this.getAdvertisementName()+".getItem : "+publicKey+"\n\t is unknown !");
			return null;
		}
		for (Item item : items) {
			if(item.getOwner().equals(publicKey) && item.getTitle().equals(title))
				return item;
		}
		return null;
	}
	
	/**
	 * Use to found a specific item possessed by current User with title
	 * @param title
	 * @return
	 */
	public Item getItemCurrentUser(String title){
		if(currentUser == null){
			System.err.println(this.getAdvertisementName()+".getItemCurrentUser : none logged user !");
			return null;
		}
		return getItem(currentUser.getKeys().getPublicKey().toString(16), title);
	}
	
	/**
	 * Return the user's messages' list
	 * @param publicKey the user public key
	 * @return a new list containing user's messages
	 */
	public ArrayList<Message> getUserMessages(String publicKey) {
		ArrayList<Message> userMessages = new ArrayList<Message>();
		for(Message m : messages){
			if(m.getOwner().equals(publicKey)) {
				userMessages.add(m);
			}
		}
		return userMessages;
	}
	
	/**
	 * Get the current user conversations. If the conversations doesn't exist, it will be created.
	 * @return a Conversations
	 */
	public Conversations getCurrentUserConversations() {
		if(currentUser == null) {
			System.err.println("no user logged");
			return null;
		}
		if(!conversations.containsKey(currentUser.getKeys().getPublicKey().toString(16))) {
			addConversations(new Conversations(currentUser.getKeys().getPublicKey().toString(16)));
		}
		return conversations.get(currentUser.getKeys().getPublicKey().toString(16));
	}
	
	/**
	 * Add an existing conversation to this manager.
	 * @param c
	 */
	public void addConversations(Conversations c) {
		conversations.put(c.getOwner(), c);
	}
	
	
	////////////////////////////////////////////////////// OTHER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * 
	 * @param user
	 * @return
	 */
	public void registration(User user){
		if(user == null)
			return;
		AsymKeysImpl originalKey = user.getKeys().clone();
		user.encryptPrivateKey(user.getClearPwd());
		user.sign(originalKey);
		this.addUser(user);
	}
	
	/**
	 * Retrieve the corresponding user according to nickname and password.
	 * @param nickname
	 * @param password
	 */
	public boolean login(String nickname, String password) {
		User u = null;
		if(users.size()>0)
			u = this.getNamed(nickname);
		// Search on network
		Search<User> search = new Search<User>(network.getGroup("users").getDiscoveryService(), "nick", true);
		search.search(nickname, VARIABLE.CheckTimeAccount, VARIABLE.ReplicationsAccount);
		ArrayList<User> results = search.getResults();
		if(results.isEmpty() && u==null){
			System.err.println("Account not found !");
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
		this.addUser(u.clone());
		// Check password
		if(!u.isPassword(password))
			return false;
		// Check privateKey decryption
		if(!u.decryptPrivateKey(password))
			return false;
		currentUser = u;
		currentUser.setClearPassword(password);
		return currentUser != null;
	}
	
	public void logout() {
		currentUser.setClearPassword(null);
		currentUser = null;
	}
	
	private void publishUsers() {
		DiscoveryService discovery = network.getGroup("users").getDiscoveryService();
		for(User u: users.values()) {
			try {
				discovery.flushAdvertisement(u);
				discovery.publish(u); //"i have this user"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void publishItems() {
		// TODO
	}
	
	private void publishMessages() {
		// TODO
	}
	
	/**
	 * Publish (advertise) users and item on network. Also check data resilience and send data to other
	 * peers if needed.
	 */
	public void publishManager() {
		publishUsers();
		publishItems();
		publishMessages();
	}
	
	////////////////////////////////////////////////// MAIN FOR TEST \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public static void main(String[] args) {
		Network network = new Network(123, VARIABLE.NetworkFolderName, VARIABLE.NetworkPeerName);
		Manager manager = new Manager(network);
		
		User user1 = new User("user1", "pass2", "name1", "firstname1", "email1", "phone1");
		/*
		Manager manager = new Manager(null);
		User user1 = new User("user1", "pass2", "name1", "firstname1", "email1", "phone1");
		user1.sign(user1.getKeys());
		User user2 = new User("user2", "pass2", "name2", "firstname2", "email2", "phone2");
		user2.sign(user2.getKeys());
		Item item1 = new Item(user1, "patate", new Category(Category.CATEGORY.Appliances), 
				"osef", null, "france", "???", 145L, 1000L, Item.TYPE.WISH);
		item1.sign(user1.getKeys());
		Item item2 = new Item(user2, "carotte", new Category(Category.CATEGORY.Appliances), 
				"osef", null, "france", "???", 145L, 1000L, Item.TYPE.WISH);
		item2.sign(user2.getKeys());
		manager.addUser(user1);
		manager.addUser(user2);
		manager.addItem(item1);
		manager.addItem(item2);
		
		Manager manager2 = new Manager(manager.toString(), null);
		if(manager2.toString().equals(manager.toString())) {
			System.out.println("ok !");
		}
		*/
	}
	
	
}

