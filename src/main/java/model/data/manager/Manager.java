package model.data.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import model.advertisement.AbstractAdvertisement;
import model.data.deal.Deal;
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.user.Conversations;
import model.data.user.Message;
import model.data.user.User;
import model.network.Network;
import model.network.NetworkInterface;
import model.network.communication.service.ServiceListener;
import model.network.search.Search;
import net.jxta.discovery.DiscoveryService;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import util.StringToElement;
import util.VARIABLES;
import util.secure.AsymKeysImpl;

/**
 * Local manager for Users, items and messages.
 * @author Julien Prudhomme
 * @author Michael Dubuis
 *
 */
public class Manager extends AbstractAdvertisement implements ServiceListener<Manager>, RecoveryManager {
	
	private HashMap<String, User> users;		// The string key is the user's public key in hexadecimal
	private ArrayList<Item> items;				// list of items handled by this manager.
	private NetworkInterface network;
	private User currentUser;					// User logged
	private ArrayList<Message> messages;		// Messages for users attempting to be received.
	private HashMap<String, Conversations> conversations; //users's conversation (already received.) (string : user public key that own the conversations
	private HashMap<String, ArrayList<Deal>> deals; // TODO add methods
	
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
	
	public void addUser(User u, boolean publish) {
		addUser(u);
		if(publish) {
			try {
				this.network.getGroup("users").getDiscoveryService().publish(u);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		if(owner == null || owner.isEmpty()){
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
			}else{
				items.remove(i);
			}
		}
		items.add(i);
	}
	
	public void addItem(Item i, boolean publish) {
		addItem(i);
		if(publish) {
			try {
				this.network.getGroup("items").getDiscoveryService().publish(i);
				this.network.getGroup("items").getDiscoveryService().remotePublish(i);
				System.out.println("item publie :");
				System.out.println(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
			System.err.println(this.getAdvertisementName()+" : Bad Signature for Message");
			return;
		}
		if(messages.contains(m)){
			if(messages.get(messages.indexOf(m)).getLastUpdated() >= m.getLastUpdated()){
				System.err.println(this.getAdvertisementName()+" : This message is already registred !");
				return;
			}
		}
		/* TODO future change (remove comment)
		if(m.getOwner().equals(currentUser.getKeys().getPublicKey().toString(16)) || m.getTo().equals(currentUser.getKeys().getPublicKey()))
			conversations.get(m.getOwner()).addMessage(m, currentUser.getKeys());
		else
		*/	
		messages.add(m);
	}
	
	/**
	 * Add an existing conversation to this manager.
	 * @param c
	 */
	public void addConversations(Conversations c) {
		if(c == null){
			System.err.println(this.getAdvertisementName()+" : This Conversation is null !");
			return;
		}
		String owner = c.getOwner();
		if(owner.isEmpty()){
			System.err.println(this.getAdvertisementName()+" : No owner found !");
			return;
		}
		if(!users.containsKey(owner)){
			System.err.println(this.getAdvertisementName()+" : Owner unknown "+owner);
			return;
		}
		if(!c.checkSignature(users.get(owner).getKeys())){
			System.err.println(this.getAdvertisementName()+" : Bad Signature for Conversation");
			return;
		}
		conversations.put(c.getOwner(), c);
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
		//s.append(conversations.get(publicKey).toString());
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
			// NO POSSIBLE TEST IF NOT OWNER ELSE ADD TO CONVERSATION
			if(message.getOwner().equals(currentUser.getKeys().getPublicKey().toString(16)))
				this.getCurrentUserConversations().addMessage(message, currentUser.getKeys());
			else
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
	 * Get the user conversations. If the conversations doesn't exist, it will be created.
	 * @param publicKey
	 * @return
	 */
	public Conversations getUserConversations(String publicKey){
		/*if(!conversations.containsKey(publicKey))
			addConversations(new Conversations(publicKey));*/
		return conversations.get(publicKey);
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
	
	public ArrayList<Deal> getUserDeals(String publicKey){
		if(deals.containsKey(publicKey))
			return deals.get(publicKey);
		return null;
	}
	
	/**
	 * Get the current user's deals. If doesn't exist, it will be created
	 * @return ArrayList<Deal>
	 */
	public ArrayList<Deal> getDealsCurrentUser(){
		if(currentUser == null) {
			System.err.println("no user logged");
			return null;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Deal>());
		return deals.get(publicKey);
	}
	////////////////////////////////////////////////////// OTHER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * 
	 * @param user
	 * @return
	 */
	public void registration(User user){
		if(user == null){
			System.err.println(Manager.class.getName()+".registration : can't register null user");
			return;
		}
		if(user.getKeys() == null || !user.getKeys().isCompatible()){
			System.err.println(Manager.class.getName()+".registration : can't register user without compatible keys !");
			return;
		}
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
		search.search(nickname, VARIABLES.CheckTimeAccount, VARIABLES.ReplicationsAccount);
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
		if(this.getNamed(nickname) != null)
			this.addUser(u);
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
		this.saving(VARIABLES.ManagerFileName);
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
		DiscoveryService discovery = network.getGroup("items").getDiscoveryService();
		for(Item i: items) {
			try {
				discovery.flushAdvertisement(i);
				discovery.publish(i); //"i have this item"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	
	

	///////////////////////////////////////////////////// RECOVERY \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	public void recovery(String path) {
		if(path == null || path.isEmpty())
			path = "./"+VARIABLES.ManagerFileName;
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(path);
		try {
			Document document = (Document) builder.build(xmlFile);
			Element root = document.getRootElement();
			Element usersElement = root.getChild("users");
			for (Element e : usersElement.getChildren()) {
				addUser(new User(e));
			}
			Element itemsElement = root.getChild("items");
			for (Element e : itemsElement.getChildren()) {
				addItem(new Item(e));
			}
			Element messagesElement = root.getChild("messages");
			for (Element e : messagesElement.getChildren()) {
				addMessage(new Message(e));
			}
			Element conversationsElement = root.getChild("ReceivedMessages");
			for (Element e : conversationsElement.getChildren()) {
				addConversations(new Conversations(e));
			}
		} catch (FileNotFoundException e1){
			System.err.println(Manager.class.getName()+".recovery : File \""+path+"\" doesn't exist");
		} catch (IOException e2) {
			System.err.println(Manager.class.getName()+".recovery : "+e2.toString());
		} catch (JDOMException e3) {
			System.err.println(Manager.class.getName()+".recovery : File \""+path+"\" is empty");
		}
	}

	@Override
	public void saving(String path) {
		Manager manager = new Manager(null);
		manager.recovery(path);
		
		if(path == null || path.isEmpty())
			path = "./"+VARIABLES.ManagerFileName; 
		// Element Root
		Element root = new Element(Manager.class.getName());
		// Saving current user's Keys decrypted
		AsymKeysImpl clearKeys = this.getCurrentUser().getKeys().clone();
		this.getCurrentUser().encryptPrivateKey(currentUser.getClearPwd());
		this.getCurrentUser().sign(clearKeys);
		// ArrayList are used for adding data in local file.
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Item> items = this.getUserItems(clearKeys.getPublicKey().toString(16));
		ArrayList<Message> messages = this.getUserMessages(clearKeys.getPublicKey().toString(16));
		ArrayList<Conversations> conversations = new ArrayList<Conversations>();
		
		Conversations converC = this.getUserConversations(clearKeys.getPublicKey().toString(16));
		if(converC!=null) conversations.add(converC);
		
		// Element users
		users.add(currentUser);
		Element usersElement = new Element("users");
		usersElement.addContent(this.getCurrentUser().getRootElement());
		for (User user : manager.getUsers()){
			if(!user.getKeys().getPublicKey().equals(currentUser.getKeys().getPublicKey())){
				usersElement.addContent(user.getRootElement());
				users.add(user);
				for (Item i : this.getUserItems(user.getKeys().getPublicKey().toString(16))) {
					if(!items.contains(i))
						items.add(i);
				}
				for (Item i : manager.getUserItems(user.getKeys().getPublicKey().toString(16))) {
					if(!items.contains(i))
						items.add(i);
				}
				for (Message m : this.getUserMessages(user.getKeys().getPublicKey().toString(16))) {
					if(!messages.contains(m))
						messages.add(m);
				}
				for (Message m : manager.getUserMessages(user.getKeys().getPublicKey().toString(16))) {
					if(!messages.contains(m))
						messages.add(m);
				}
				Conversations c;
				c = this.getUserConversations(user.getKeys().getPublicKey().toString(16));
				if(c != null && !conversations.contains(c))
					conversations.add(c);
				c = manager.getUserConversations(user.getKeys().getPublicKey().toString(16));
				if(c != null && !conversations.contains(c))
					conversations.add(c);
			}
		}
		// Element items
		Element itemsElement = new Element("items");
		for (Item i : items) {
			itemsElement.addContent(i.getRootElement());
		}
		// Element messages
		Element messagesElement = new Element("messages");
		for (Message m : messages) {
			messagesElement.addContent(m.getRootElement());
		}
		// Element ReceivedMessages
		Element conversationsElement = new Element("ReceivedMessages");
		for (Conversations c : conversations) {
			conversationsElement.addContent(c.getRootElement());
		}
		// Adding all elements in root element
		root.addContent(usersElement);
		root.addContent(itemsElement);
		root.addContent(messagesElement);
		root.addContent(conversationsElement);
		// Writing in file
		Document doc = new Document(root);
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(doc, new FileWriter(path));
		} catch (IOException e) {
			System.err.println(Manager.class.getName()+".saving : "+e.toString());
		}
		// Decrypt current user's private key
		currentUser.decryptPrivateKey(currentUser.getClearPwd());
	}
	////////////////////////////////////////////////// MAIN FOR TEST \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public static void main(String[] args) {
		Network network = new Network(123, VARIABLES.NetworkFolderName, VARIABLES.NetworkPeerName);
		Manager manager = new Manager(network);
		
		User user = new User("user1", "pass2", "name1", "firstname1", "email1", "phone1");
		manager.registration(user);
		user.decryptPrivateKey("pass2");
		manager.currentUser = user;
		
		Item item = new Item(manager.currentUser, "title", new Category("category"), "description", "image", "country", "contact", 0L, 0L, TYPE.WISH);
		item.sign(manager.currentUser.getKeys());
		manager.addItem(item);
		
		manager.saving("");
		
		Manager manager2 = new Manager(null);
		manager2.recovery("");
		
		System.out.println(manager2.toString());
	}
	
	
}

