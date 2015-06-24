package model.data.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import model.advertisement.AbstractAdvertisement;
import model.data.contrat.Contrat;
import model.data.favorites.Favorites;
import model.data.item.Item;
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
	private HashMap<String, ArrayList<Contrat>> deals;
	private HashMap<String, Favorites> favorites;

	///////////////////////////////////////////////// CONSTRUCTORS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Create a manager based on a String that is XML formated.
	 * @param XML
	 * @param network
	 */
	public Manager(String XML, NetworkInterface network) {
		super(XML);
		this.network = network;
	}
	/**
	 * Create an empty manager
	 * @param network
	 */
	public Manager(NetworkInterface network) {
		super();
		this.network = network;
	}
	//////////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Collection<User> getUsers() {
		return users.values();
	}
	/**
	 * Return the user with this publicKey
	 * @param publicKey - String format
	 * @return
	 */
	public User getUser(String publicKey){
		return users.get(publicKey);
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
	 * Get the current User. If no user logged, return null.
	 * @return
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	/**
	 * Return user who has this item
	 * @param item
	 * @return
	 */
	public User whoHas(Item item){
		return users.get(item.getOwner());
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
	 * Use to found a item with itemKey
	 * @param itemKey
	 * @return
	 */
	public Item getItem(String itemKey){
		if(itemKey == null || itemKey.isEmpty()){
			System.err.println(this.getAdvertisementName()+".getItem : itemKey is empty or null !");
			return null;
		}
		for (Item item : items) {
			if(item.getItemKey().equals(itemKey))
				return item;
		}
		return null;
	}
	/**
	 * Use to found a item with owner's publicKey and item's title
	 * @param publicKey
	 * @param title
	 * @return
	 * @deprecated
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
	 * @deprecated
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
	/**
	 * Get the current user's deals. If doesn't exist, return create new ArrayList;
	 * @return ArrayList<Deal>
	 */
	public ArrayList<Contrat> getUserDeals(String publicKey){
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		return deals.get(publicKey);
	}
	/**
	 * Get the current user's deals. If doesn't exist, it will be created
	 * @return ArrayList<Deal>
	 */
	public ArrayList<Contrat> getDealsCurrentUser(){
		if(currentUser == null) {
			System.err.println("no user logged");
			return null;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		return getUserDeals(publicKey);
	}
	/**
	 * Get the current user's favorites. If doesn't exist, return null;
	 * @return Favorites
	 */
	public Favorites getUserFavorites(String publicKey){
		if(!favorites.containsKey(publicKey))
			return null;
		return favorites.get(publicKey);
	}
	/**
	 * Get the current user's favorites. If doesn't exist, it will return null.
	 * @return Favorites
	 */
	public Favorites getFavoritesCurrentUser(){
		if(currentUser == null) {
			System.err.println("no user logged");
			return null;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!favorites.containsKey(publicKey))
			favorites.put(publicKey, null);
		return getUserFavorites(publicKey);
	}
	///////////////////////////////////////////////////// ADDERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * to add an user in this instance of manager
	 * if user is already in the manager, this function remove old and put User u
	 * @param u - User to add
	 */
	public void addUser(User u){
		if(u == null){
			printError("addUser","This User is empty !");
			return;
		}
		if(!u.checkSignature(u.getKeys())){
			printError("addUser","Bad Signature for "+u.getNick());
			return;
		}
		String key = u.getKeys().getPublicKey().toString(16);
		if(users.containsKey(key)){
			if(users.get(key).equals(u) && users.get(key).getLastUpdated() >= u.getLastUpdated()){
				printError("addUser","User "+u.getNick()+" is already registred !");
				return;
			}
		}
		users.put(key, u);
		deals.put(key, new ArrayList<Contrat>());
	}
	public void addUser(User u, boolean publish) {
		addUser(u);
		if(publish) {
			try {
				this.network.getGroup("users").getDiscoveryService().publish(u);
			} catch (IOException e) {
				printError("addUser",e.toString());
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
			printError("addItem","This Item is empty !");
			return;
		}
		String owner = i.getOwner();
		if(owner == null || owner.isEmpty()){
			printError("addItem","No owner found !");
			return;
		}
		if(!users.containsKey(owner)){
			printError("addItem","Owner unknown for "+i.getTitle());
			return;
		}
		if(!i.checkSignature(users.get(owner).getKeys())){
			printError("addItem","Bad Signature for "+i.getTitle());
			return;
		}
		if(items.contains(i)){
			if(items.get(items.indexOf(i)).getLastUpdated() >= i.getLastUpdated()){
				printError("addItem","Item "+i.getTitle()+" is already registred !");
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
				printError("addItem",e.toString());
			}
		}
	}
	public void updateItem(String itemKey, Item item){
		removeItem(getItem(itemKey));
		addItem(item);
	}
	/**
	 * to add a message
	 * if receiver of the message isn't registered in this instance of manger, function will fail
	 * @param m
	 */
	public void addMessage(Message m){
		if(m == null){
			printError("addMessage","This Message is null !");
			return;
		}
		String owner = m.getOwner();
		if(owner.isEmpty()){
			printError("addMessage","No owner found !");
			return;
		}
		if(!users.containsKey(owner)){
			printError("addMessage","Owner unknown "+owner);
			return;
		}
		if(!m.checkSignature(users.get(owner).getKeys())){
			printError("addMessage","Bad Signature for Message");
			return;
		}
		if(messages.contains(m)){
			if(messages.get(messages.indexOf(m)).getLastUpdated() >= m.getLastUpdated()){
				printError("addMessage","This message is already registred !");
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
			printError("addConversations","This Conversation is null !");
			return;
		}
		String owner = c.getOwner();
		if(owner.isEmpty()){
			printError("addConversations","No owner found !");
			return;
		}
		if(!users.containsKey(owner)){
			printError("addConversations","Owner unknown "+owner);
			return;
		}
		if(!c.checkSignature(users.get(owner).getKeys())){
			printError("addConversations","Bad Signature for Conversation");
			return;
		}
		conversations.put(c.getOwner(), c);
	}
	/**
	 * Add Favorites to the owner of the Favorites. If the user isn't in the manager, abort.
	 * @param f
	 */
	public void addFavorites(Favorites f){
		if(f == null){
			printError("addFavorites","This Favorites is null !");
			return;
		}
		String owner = f.getOwner();
		if(owner.isEmpty()){
			printError("addFavorites","No owner found !");
			return;
		}
		if(!users.containsKey(owner)){
			printError("addFavorites","Owner unknown "+owner);
			return;
		}
		if(!f.checkSignature(users.get(owner).getKeys())){
			printError("addFavorites","Bad Signature for Favorite");
			return;
		}
		favorites.put(f.getOwner(), f);
	}
	/**
	 * Add an item to current user's Favorites
	 * @param item
	 */
	public void addFavoritesItem(Item item){
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(publicKey == null || publicKey.isEmpty()){
			printError("addFavoritesItem", "Not user logged or PublicKey empty !");
			return;
		}
		if(!favorites.containsKey(publicKey)){
			Favorites f = new Favorites(currentUser);
			f.sign(currentUser.getKeys());
			addFavorites(f);
		}
		if(item == null){
			printError("addFavoritesItem","This Item is null !");
			return;
		}
		int i = 0;
		for(String s : favorites.keySet()){
			System.out.println(i+" : "+s);
			i++;
		}
		favorites.get(publicKey).addItem(item); // TODO Je comprends pas l'erreur !!!
		favorites.get(publicKey).sign(currentUser.getKeys());
	}
	/**
	 * Create a new empty Deal for the current User
	 * @param title
	 */
	public void newDeal(String title){
		if(currentUser == null){
			printError("newDeal", "No user logged");
			return;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		Contrat deal = new Contrat(title, currentUser);
		deals.get(publicKey).add(deal);
	}
	/**
	 * Add Deal to the user's publicKey. If deal is empty, it will abort.
	 * If the publicKey isn't an user's publicKey known, it will abort.
	 * @param publicKey
	 * @param deal
	 */
	public void addDeal(String publicKey, Contrat deal){
		if(deal == null){
			printError("addDeal", "deal is empty");
			return;
		}
		if(!users.containsKey(publicKey)){
			printError("addDeal", "user is unknow");
			return;
		}
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		deals.get(publicKey).add(deal);
	}
	/////////////////////////////////////////////////// REMOVERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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
	 * Remove a message from the Manager
	 * @param msg
	 * @return
	 */
	public boolean removeMessage(Message msg){
		return messages.remove(msg);
	}
	//////////////////////////////////////////////////// PRINTER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static void printInfo(String method, String info){
		System.out.println("INFO : "+Manager.class.getName()+"."+method+" : "+info);
	}
	private static boolean printError(String method, String error){
		System.err.println("ERROR : "+Manager.class.getName()+"."+method+" : "+error);
		return false;
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
		s.append("<items>");
		for(Item i : getUserItems(publicKey)) {
			s.append(i.toString());
		}
		s.append("</items>");
		s.append("<messages>");
		for(Message m : getUserMessages(publicKey)){
			s.append(m.toString());
		}
		s.append("</messages>");
		s.append("<ReceivedMessages>");
		s.append(conversations.get(publicKey).toString());
		s.append("</ReceivedMessages>");
		s.append("<favorites>");
		s.append(favorites.get(publicKey).toString());
		s.append("</favorites>");
		s.append("<deals>");
		for(Contrat d : getUserDeals(publicKey)){
			s.append(d.toString());
		}
		s.append("</deals>");
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
	 * Get an XML string representing all the favorites that are saved on this device.
	 * @return A string, XML formated
	 */
	private String getFavoritesXML(){
		StringBuffer s = new StringBuffer();
		for(Favorites f : favorites.values()) {
			s.append(f);
		}
		return s.toString();
	}
	/**
	 * Get an XML string representing all the deals that are saved on this device.
	 * @return A string, XML formated
	 */
	private String getDealsXML(){
		StringBuffer s = new StringBuffer();
		for(Entry<String, ArrayList<Contrat>> entry : this.deals.entrySet()) {
			String owner = entry.getKey();
			ArrayList<Contrat> deals = entry.getValue();
			for (Contrat d : deals) {
				s.append("<deal>");
				s.append("<owner>");
				s.append(owner);
				s.append("</owner>");
				s.append(d);
				s.append("</deal>");
			}
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
	/**
	 * Load all the favorites in this element
	 * @param e an element that contains messages in XML format.
	 */
	private void loadFavorites(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element f: root.getChildren()){
			addFavorites(new Favorites(f));
		}
	}
	/**
	 * Load all the deals in this element
	 * @param e an element that contains messages in XML format.
	 */
	private void loadDeals(Element e){
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element d: root.getChildren()){
			String owner = d.getChildText("owner");
			Element deal = d.getChild("Deal");
			addDeal(owner, new Contrat(deal));
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
		case "favorites":			loadFavorites(e); break;
		case "deals":				loadDeals(e); break;
		default: return false;
		}
		return true;
	}
	@Override
	protected String getAdvertisementName() {
		return Manager.class.getName();
	}
	@Override
	protected void setKeys() {
		users = new HashMap<String, User>();
		items = new ArrayList<Item>();
		messages = new ArrayList<Message>();
		conversations = new HashMap<String, Conversations>();
		currentUser = null;
		favorites = new HashMap<String, Favorites>();
		deals = new HashMap<String, ArrayList<Contrat>>();
		addKey("users", false, true);
		addKey("items", false, true);
		addKey("messages", false, true);
		addKey("ReceivedMessages", false, true);
		addKey("favorites", false, true);
		addKey("deals", false, true);
	}
	@Override
	protected void putValues() {
		addValue("users", getUsersXML());
		addValue("items", getItemsXML());
		addValue("messages", getMessagesXML());
		addValue("ReceivedMessages", getReceivedMessagesXML());
		addValue("favorites", getFavoritesXML());
		addValue("deals", getDealsXML());
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
			if(message.getOwner().equals(currentUser.getKeys().getPublicKey().toString(16))){
				if(message.checkSignature(message.getSender(currentUser.getKeys()))) // If owner, check signature
					this.getCurrentUserConversations().addMessage(message, currentUser.getKeys());
				else
					printError("messageEvent", "Bad Signature for Message");
			}
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
		// Add all Favorites
		elements = null;
		elements  = StringToElement.getElementFromString(m.getFavoritesXML(), "favorites");
		for (Element element : elements.getChildren()) {
			Favorites favorites = new Favorites(element);
			if(favorites.checkSignature(this.getUser(favorites.getOwner()).getKeys()))
				this.addFavorites(favorites);
		}
	}
	///////////////////////////////////////////////////// UTILS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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
	///////////////////////////////////////////////////// OTHERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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
		// TODO This method have to change if nickName can be same on different account !
		User u = null;
		if(users.size()>0)
			u = this.getNamed(nickname);
		// Search on network
		Search<User> search = new Search<User>(network.getGroup("users").getDiscoveryService(), "nick", true);
		search.search(nickname, VARIABLES.CheckTimeAccount, VARIABLES.ReplicationsAccount);
		ArrayList<User> results = search.getResults();
		if(results.isEmpty() && u==null){
			printError("login", "Account not found !");
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
	/**
	 * Log out the current User.
	 */
	public void logout() {
		AsymKeysImpl clearKey = currentUser.getKeys().clone();
		String clearPassword = new String(currentUser.getClearPwd());
		System.out.println("\tCOMPATIBLE : "+currentUser.getKeys().isCompatible());
		currentUser.encryptPrivateKey(clearPassword);
		currentUser.sign(clearKey);
		System.out.println("\tSIGNATURE : "+currentUser.checkSignature(currentUser.getKeys()));
		currentUser.setClearPassword(null);
		this.saving(VARIABLES.ManagerFilePath);
		currentUser = null;
	}
	/////////////////////////////////////////////////// PUBLISHERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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
			path = VARIABLES.ManagerFilePath;
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(path);
		boolean recovered = true;
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
			Element favoritesElement = root.getChild("favorites");
			for (Element e : favoritesElement.getChildren()) {
				addFavorites(new Favorites(e));
			}
			Element dealsElement = root.getChild("deals");
			for	(Element e : dealsElement.getChildren()){
				String owner = e.getChild("owner").getText();
				//String owner = e.getChildText("owner");
				if(!deals.containsKey(owner) && users.containsKey(owner))
					deals.put(owner, new ArrayList<Contrat>());
				if(e.getChild(Contrat.class.getName())!=null)
					addDeal(owner, new Contrat(e.getChild("Deal")));
			}
		} catch (FileNotFoundException e){
			recovered = printError("recovery", "File \""+path+"\" doesn't exist");
		} catch (IOException e) {
			recovered = printError("recovery", "IOException\n\t"+e.toString());
		} catch (JDOMException e) {
			recovered = printError("recovery", "JDOMException\n\tFile \""+path+"\" is empty");
			xmlFile.delete();
		} catch (Exception e){
			recovered = printError("recovery", "Unknown error\n\t"+e.toString());
			e.printStackTrace();
		} finally{
			if(recovered)
				printInfo("recovery", "Local data recovered");
		}
	}
	@Override
	public void saving(String path) {
		String currentPublicKey = this.getCurrentUser().getKeys().getPublicKey().toString(16);
		// Recovery all local data in a new Manager
		Manager manager = new Manager(null);
		manager.recovery(path);
		
		if(path == null || path.isEmpty())
			path = VARIABLES.ManagerFilePath; 
		// Element Root
		Element root = new Element(Manager.class.getName());
		// ArrayList are used for adding data in local file.
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Item> items = this.getUserItems(currentPublicKey);
		ArrayList<Message> messages = this.getUserMessages(currentPublicKey);
		ArrayList<Conversations> conversations = new ArrayList<Conversations>();
		ArrayList<Favorites> favorites = new ArrayList<Favorites>();
		HashMap<String,ArrayList<Contrat>> deals = new HashMap<String,ArrayList<Contrat>>();
		
		Conversations converC = this.getUserConversations(currentPublicKey);
		if(converC!=null) conversations.add(converC);
		ArrayList<Contrat> arrayDealsC = this.getDealsCurrentUser();
		if(arrayDealsC!=null) deals.put(currentPublicKey, arrayDealsC);
		Favorites favoC = this.getFavoritesCurrentUser();
		if(favoC!=null) favorites.add(favoC);
		
		// Element users
		users.add(this.getCurrentUser());
		Element usersElement = new Element("users");
		usersElement.addContent(this.getCurrentUser().getRootElement());
		for (User user : manager.getUsers()){
			String userKey =  user.getKeys().getPublicKey().toString(16);
				if(!user.getKeys().getPublicKey().toString(16).equals(currentPublicKey)){
					usersElement.addContent(user.getRootElement());
					users.add(user);
				// Filling ArrayList items
				for (Item i : this.getUserItems(userKey)) {
					if(!items.contains(i))
						items.add(i);
				}
				for (Item i : manager.getUserItems(userKey)) {
					if(!items.contains(i))
						items.add(i);
				}
				// Filling ArrayList messages
				for (Message m : this.getUserMessages(userKey)) {
					if(!messages.contains(m))
						messages.add(m);
				}
				for (Message m : manager.getUserMessages(userKey)) {
					if(!messages.contains(m))
						messages.add(m);
				}
				// Filling ArrayList conversations
				Conversations c;
				c = this.getUserConversations(userKey);
				if(c != null && !conversations.contains(c))
					conversations.add(c);
				c = manager.getUserConversations(userKey);
				if(c != null && !conversations.contains(c))
					conversations.add(c);
				// Filling ArrayList favorites
				Favorites f;
				f = this.getUserFavorites(userKey);
				if(f != null && !favorites.contains(f))
					favorites.add(f);
				f = manager.getUserFavorites(userKey);
				if(f != null && !favorites.contains(f))
					favorites.add(f);
				// Filling ArrayList deals
				if(!deals.containsKey(userKey))
					deals.put(userKey, new ArrayList<Contrat>());
				for (Contrat d : this.getUserDeals(userKey)){
					if(!deals.get(userKey).contains(d))
						deals.get(userKey).add(d);
				}
				for (Contrat d : manager.getUserDeals(userKey)){
					if(!deals.get(userKey).contains(d))
						deals.get(userKey).add(d);
				}
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
		// Element Favorites
		Element favoritesElement = new Element("favorites");
		for (Favorites f : favorites) {
			favoritesElement.addContent(f.getRootElement());
		}
		// Element Deals
		Element dealsElement = new Element("deals");
		for (User u : users) {
			String userKey = u.getKeys().getPublicKey().toString(16);
			for(Contrat d : deals.get(userKey)){
				Element ownerElement = new Element("owner");
				Element dealElement = new Element("deal");
				ownerElement.addContent(userKey);
				dealElement.addContent(ownerElement);
				dealElement.addContent(d.getRootElement());
				dealsElement.addContent(dealElement);
			}
		}
		// Adding all elements in root element
		root.addContent(usersElement);
		root.addContent(itemsElement);
		root.addContent(messagesElement);
		root.addContent(conversationsElement);
		root.addContent(favoritesElement);
		root.addContent(dealsElement);
		// Writing in file
		Document doc = new Document(root);
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(doc, new FileWriter(path));
		} catch (IOException e) {
			printError("saving", "saving : "+e.toString());
		}
		// Decrypt current user's private key
		// currentUser.decryptPrivateKey(currentUser.getClearPwd());
	}
	////////////////////////////////////////////////// MAIN FOR TEST \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public static void main(String[] args) {
		Network network = new Network(123, VARIABLES.NetworkFolderName, VARIABLES.NetworkPeerName);
		Manager manager = new Manager(network);
		
		/*User user1 = new User("Eldoran", "123456789", "Michael", "Dubuis", "Eldoran.s.e@gmail.com", "0664968765");
		manager.registration(user1);
		user1.decryptPrivateKey("123456789");
		manager.currentUser = user1;
		Item item1 = new Item(manager.currentUser, "Eldoran's Soul", new Category("category"), "useless things", "", "hell", "phone me", 0L, 0L, TYPE.PROPOSAL);
		item1.sign(manager.currentUser.getKeys());
		manager.addItem(item1);
		
		User user2 = new User("Lulu", "666", "Satan", "Lucifer", "Lulu666@hell.ff", "666");
		user2.sign(user2.getKeys());
		
		Item item2 = new Item(user2, "Porsche 911", new Category("Vehicles"), "Beautyful car", "", "In front of your Home", "praying", 0L, 0L, TYPE.PROPOSAL);
		item2.sign(user2.getKeys());
		
		Deal deal = new Deal("Good deal", user1);
		deal.addSignatory(user2);
		deal.addItem(item1);
		deal.addItem(item2);
		deal.addTransferRule(item1.getItemKey(), user2.getKeys().getPublicKey().toString(16));
		deal.addTransferRule(item2.getItemKey(), user1.getKeys().getPublicKey().toString(16));
		deal.sign(user1.getKeys()); 
		
		manager.addDeal(user1.getKeys().getPublicKey().toString(16), deal);
		
		manager.saving("");*/
		
		manager.recovery("");
		for (User user : manager.getUsers()) {
			for (Contrat deal : manager.getUserDeals(user.getKeys().getPublicKey().toString(16))) {
				System.out.println(deal.toPrint());
			}
		}
	}
	
	
}

