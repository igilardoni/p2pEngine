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
import model.data.user.UserMessage;
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

import util.Printer;
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
	
	private UserManager users;
	private ItemManager items;
	
	private NetworkInterface network;
	
	private ArrayList<UserMessage> messages;		// Messages for users attempting to be received.
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
	
	/**
	 * Get the UserManager that is handling users.
	 * @return an UserManager instance.
	 */
	public UserManager getUsers() {
		return users;
	}
	
	/**
	 * Get the ItemManager that is handling items.
	 * @return an ItemManager instance.
	 */
	public ItemManager getItems() {
		return items;
	}
	
	public NetworkInterface getNetwork() {
		return network;
	}
	
	public HashMap<String, ArrayList<Contrat>> getDeals() {
		return deals;
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
		if(users.getCurrentUser() == null) {
			System.err.println("no user logged");
			return null;
		}
		User u = users.getCurrentUser();
		if(!conversations.containsKey(u.getKeys().getPublicKey().toString(16))) {
			addConversations(new Conversations(u.getKeys().getPublicKey().toString(16)));
		}
		return conversations.get(u.getKeys().getPublicKey().toString(16));
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
		User currentUser = users.getCurrentUser();
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
		User currentUser = users.getCurrentUser();
		if(currentUser == null) {
			System.err.println("no user logged");
			return null;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!favorites.containsKey(publicKey))
			favorites.put(publicKey, new Favorites(currentUser));
		return getUserFavorites(publicKey);
	}
	///////////////////////////////////////////////////// ADDERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/**
	 * Add an existing conversation to this manager.
	 * @param c
	 */
	public void addConversations(Conversations c) {
		if(c == null){
			Printer.printError(this, "addConversations","This Conversation is null !");
			return;
		}
		String owner = c.getOwner();
		if(owner.isEmpty()){
			Printer.printError(this, "addConversations","No owner found !");
			return;
		}
		if(users.getUser(owner) == null){
			Printer.printError(this, "addConversations","Owner unknown "+owner);
			return;
		}
		if(!c.checkSignature(users.getUser(owner).getKeys())){
			Printer.printError(this, "addConversations","Bad Signature for Conversation");
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
			Printer.printError(this, "addFavorites","This Favorites is null !");
			return;
		}
		String owner = f.getOwner();
		if(owner.isEmpty()){
			Printer.printError(this, "addFavorites","No owner found !");
			return;
		}
		if(users.getUser(owner) == null){
			Printer.printError(this, "addFavorites","Owner unknown "+owner);
			return;
		}
		if(!f.checkSignature(users.getUser(owner).getKeys())){
			Printer.printError(this, "addFavorites","Bad Signature for Favorite");
			return;
		}
		favorites.put(f.getOwner(), f);
	}
	/**
	 * Add an item to current user's Favorites
	 * @param item
	 */
	public void addFavoritesItem(Item item){
		User currentUser = users.getCurrentUser();
		String publicKey = currentUser.getKeys().getPublicKey().toString(16); //TODO verification currentUser existe ?
		if(publicKey == null || publicKey.isEmpty()){
			Printer.printError(this, "addFavoritesItem", "Not user logged or PublicKey empty !");
			return;
		}
		if(!favorites.containsKey(publicKey)){
			Favorites f = new Favorites(currentUser);
			f.sign(currentUser.getKeys());
			addFavorites(f);
		}
		if(item == null){
			Printer.printError(this, "addFavoritesItem","This Item is null !");
			return;
		}
		favorites.get(publicKey).addItem(item);
		favorites.get(publicKey).sign(currentUser.getKeys());
	}
	/**
	 * Create a new empty Deal for the current User
	 * @param title
	 */
	public void newDeal(String title){
		User currentUser = users.getCurrentUser();
		if(currentUser == null){
			Printer.printError(this, "newDeal", "No user logged");
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
			Printer.printError(this, "addDeal", "deal is empty");
			return;
		}
		if(users.getUser(publicKey) == null){
			Printer.printError(this, "addDeal", "user is unknow");
			return;
		}
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		deals.get(publicKey).add(deal);
	}
	/////////////////////////////////////////////////// REMOVERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Remove a message from the Manager
	 * @param msg
	 * @return
	 */
	public boolean removeMessage(UserMessage msg){
		return messages.remove(msg);
	}

	////////////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Return an XML string containing user's info and his items and his messages.
	 * @param publicKey
	 * @return a string, XML-formated, containing the user and his objects and his messages
	 */
	public String completUserXMLString(String publicKey) {
		StringBuffer s = new StringBuffer();
		s.append(users.getUser(publicKey).toString());
		s.append("<items>");
		for(Item i : users.getUserItems(publicKey)) {
			s.append(i.toString());
		}
		s.append("</items>");
		s.append("<messages>");
		// TODO
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
	 * Get an XML string representing all the messages that are saved on this device.
	 * @return A string, XML formated
	 */
	private String getMessagesXML() {
		StringBuffer s = new StringBuffer();
		for(UserMessage m: messages) {
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
		case "users": 				users.loadUsers(e); break;
		case "items": 				items.loadItems(e); break;
		case "messages": 			/*TODO loadMessages(e);*/ break;
		case "ReceivedMessages":	loadReceivedMessages(e); break;
		case "favorites":			loadFavorites(e); break;
		case "deals":				loadDeals(e); break;
		default: return false;
		}
		return true;
	}
	
	@Override
	protected String getAdvertisementName() {
		return this.getClass().getName();
	}
	@Override
	protected void setKeys() {
		users = new UserManager(this);
		items = new ItemManager(this);
		messages = new ArrayList<UserMessage>();
		conversations = new HashMap<String, Conversations>();
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
		addValue("users", users.getUsersXML());
		addValue("items", items.getItemsXML());
		addValue("messages", getMessagesXML());
		addValue("ReceivedMessages", getReceivedMessagesXML());
		addValue("favorites", getFavoritesXML());
		addValue("deals", getDealsXML());
	}
	
	
	//TODO///////////////////////////////////////////// SERVICE LISTENER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	public void messageEvent(Manager m) {
		/*// TODO Tests
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
	*/ }

	/////////////////////////////////////////////////// PUBLISHERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private void publishMessages() {
		// TODO
	}
	
	
	/**
	 * Publish (advertise) users and item on network. Also check data resilience and send data to other
	 * peers if needed.
	 * TODO a refaire pe ?
	 */
	public void publishManager() {
		users.publishUsers();
		items.publishItems();
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
				users.addUser(new User(e));
			}
			Element itemsElement = root.getChild("items");
			for (Element e : itemsElement.getChildren()) {
				items.addItem(new Item(e));
			}
			Element messagesElement = root.getChild("messages");
			for (Element e : messagesElement.getChildren()) {
			// TODO	addMessage(new Message(e));
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
				if(!deals.containsKey(owner) && users.userExists(owner))
					deals.put(owner, new ArrayList<Contrat>());
				if(e.getChild(Contrat.class.getName())!=null)
					addDeal(owner, new Contrat(e.getChild("Deal")));
			}
		} catch (FileNotFoundException e){
			recovered = Printer.printError(this, "recovery", "File \""+path+"\" doesn't exist");
		} catch (IOException e) {
			recovered = Printer.printError(this, "recovery", "IOException\n\t"+e.toString());
		} catch (JDOMException e) {
			recovered = Printer.printError(this, "recovery", "JDOMException\n\tFile \""+path+"\" is empty");
			xmlFile.delete();
		} catch (Exception e){
			recovered = Printer.printError(this, "recovery", "Unknown error\n\t"+e.toString());
			e.printStackTrace();
		} finally{
			if(recovered)
				Printer.printInfo(this, "recovery", "Local data recovered");
		}
	}
	
	
	/**
	 * TODO FONCTION BCP TROP LONGUE LOL
	 */
	@Override
	public void saving(String path) {
		String currentPublicKey = users.getCurrentUser().getKeys().getPublicKey().toString(16);
		// Recovery all local data in a new Manager
		Manager manager = new Manager(null);
		manager.recovery(path);
		
		if(path == null || path.isEmpty())
			path = VARIABLES.ManagerFilePath; 
		// Element Root
		Element root = new Element(Manager.class.getName());
		// ArrayList are used for adding data in local file.
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Item> items = this.users.getUserItems(currentPublicKey);
		ArrayList<UserMessage> messages = null; // TODO this.getUserMessages(currentPublicKey);
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
		users.add(this.users.getCurrentUser());
		Element usersElement = new Element("users");
		usersElement.addContent(this.users.getCurrentUser().getRootElement());
		for (User user : manager.users.getUsers()){
			String userKey =  user.getKeys().getPublicKey().toString(16);
				if(!user.getKeys().getPublicKey().toString(16).equals(currentPublicKey)){
					usersElement.addContent(user.getRootElement());
					users.add(user);
				// Filling ArrayList items
				for (Item i : this.users.getUserItems(userKey)) {
					if(!items.contains(i))
						items.add(i);
				}
				for (Item i : manager.users.getUserItems(userKey)) {
					if(!items.contains(i))
						items.add(i);
				}
				// Filling ArrayList messages
			/* TODO	for (Message m : this.getUserMessages(userKey)) {
					if(!messages.contains(m))
						messages.add(m);
				}
				for (Message m : manager.getUserMessages(userKey)) {
					if(!messages.contains(m))
						messages.add(m);
				} */
				
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
		/* TODO FIX BUG
		for (Message m : messages) {
			messagesElement.addContent(m.getRootElement());
		}
		*/
		// Element ReceivedMessages
		Element conversationsElement = new Element("ReceivedMessages");
		/* TODO FIX BUG
		for (Conversations c : conversations) {
			conversationsElement.addContent(c.getRootElement());
		}
		*/
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
		/* TODO FIX BUG
		root.addContent(messagesElement);
		root.addContent(conversationsElement);
		*/
		root.addContent(favoritesElement);
		root.addContent(dealsElement);
		// Writing in file
		Document doc = new Document(root);
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(doc, new FileWriter(path));
		} catch (IOException e) {
			Printer.printError(this, "saving", "saving : "+e.toString());
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
		for (User user : manager.users.getUsers()) {
			for (Contrat deal : manager.getUserDeals(user.getKeys().getPublicKey().toString(16))) {
				System.out.println(deal.toPrint());
			}
		}
	}
	
	public void addMessage(UserMessage msg) {
		this.messages.add(msg);
		//TODO PUBLISH MSG
	}
	
	public ArrayList<UserMessage> getMessages() {
		return messages;
	}
	
	
}

