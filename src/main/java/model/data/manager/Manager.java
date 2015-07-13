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
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Item.TYPE;
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
public class Manager extends AbstractAdvertisement implements RecoveryManager {
	
	private UserManager userManager;
	private ItemManager itemManager;
	private MessageManager messageManager;
	private FavoriteManager favoriteManager;
	private ContratManager contratManager;
	
	private NetworkInterface network;

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
	 * Get an object (Item, user, message .. etc) with his id
	 * @param id
	 * @return an AbstractAdvertisement
	 */
	public AbstractAdvertisement getObjectById(String id) {
		// TODO 
		return null;
	}
	
	/**
	 * Get the UserManager that is handling users.
	 * @return an UserManager instance.
	 */
	public UserManager getUserManager() {
		return userManager;
	}
	
	/**
	 * Get the ItemManager that is handling items.
	 * @return an ItemManager instance.
	 */
	public ItemManager getItemManager() {
		return itemManager;
	}
	
	public MessageManager getMessageManager() {
		return messageManager;
	}
	
	public FavoriteManager getFavoriteManager() {
		return favoriteManager;
	}
	
	public ContratManager getContratManager() {
		return contratManager;
	}
	
	public NetworkInterface getNetwork() {
		if(network == null)
			Printer.printError(this, "getNetwork", "Network is null !");
		return network;
	}

	////////////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Return an XML string containing user's info and his items and his messages.
	 * @param publicKey
	 * @return a string, XML-formated, containing the user and his objects and his messages
	 */
	public String completUserXMLString(String publicKey) {
		StringBuffer s = new StringBuffer();
		s.append(userManager.getUser(publicKey).toString());
		s.append("<items>");
		for(Item i : userManager.getUserItems(publicKey)) {
			s.append(i.toString());
		}
		s.append("</items>");
		s.append("<messages>");
		// TODO
		s.append("</messages>");
		s.append("<ReceivedMessages>");
		s.append(messageManager.getConversation(publicKey).toString());
		s.append("</ReceivedMessages>");
		s.append("<favorites>");
		s.append(favoriteManager.getUserFavorites(publicKey).toString());
		s.append("</favorites>");
		s.append("<deals>");
		for(Contrat d : contratManager.getUserDeals(publicKey)){
			s.append(d.toString());
		}
		s.append("</deals>");
		return s.toString();
	}
	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "users": 				userManager.loadUsers(e); break;
		case "items": 				itemManager.loadItems(e); break;
		case "messages": 			/*TODO loadMessages(e);*/ break;
		case "ReceivedMessages":	messageManager.loadReceivedMessages(e); break;
		case "favorites":			favoriteManager.loadFavorites(e); break;
		case "deals":				contratManager.loadDeals(e); break;
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
		userManager = new UserManager(this);
		itemManager = new ItemManager(this);
		messageManager = new MessageManager(this);
		favoriteManager = new FavoriteManager(this);
		contratManager = new ContratManager(this);
		addKey("users", false, true);
		addKey("items", false, true);
		addKey("messages", false, true);
		addKey("ReceivedMessages", false, true);
		addKey("favorites", false, true);
		addKey("deals", false, true);
	}
	@Override
	protected void putValues() {
		addValue("users", userManager.getUsersXML());
		addValue("items", itemManager.getItemsXML());
		addValue("messages", messageManager.getMessagesXML());
		addValue("ReceivedMessages", messageManager.getReceivedMessagesXML());
		addValue("favorites", favoriteManager.getFavoritesXML());
		addValue("deals", contratManager.getDealsXML());
	}

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
		userManager.publishUsers();
		itemManager.publishItems();
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
				userManager.addUser(new User(e));
			}
			Element itemsElement = root.getChild("items");
			for (Element e : itemsElement.getChildren()) {
				itemManager.addItem(new Item(e));
			}
			Element messagesElement = root.getChild("messages");
			for (Element e : messagesElement.getChildren()) {
			// TODO	addMessage(new Message(e));
			}
			Element conversationsElement = root.getChild("ReceivedMessages");
			for (Element e : conversationsElement.getChildren()) {
				messageManager.addConversations(new Conversations(e));
			}
			Element favoritesElement = root.getChild("favorites");
			for (Element e : favoritesElement.getChildren()) {
				favoriteManager.addFavorites(new Favorites(e));
			}
			Element dealsElement = root.getChild("deals");
			for	(Element e : dealsElement.getChildren()){
				String owner = e.getChild("owner").getText(); // TODO Change with AsymKeysImpl
				//String owner = e.getChildText("owner");
				if(!contratManager.containsUser(owner) && userManager.userExists(owner))
					contratManager.getDeals().put(owner, new ArrayList<Contrat>());
				if(e.getChild(Contrat.class.getName())!=null)
					contratManager.addDeal(owner, new Contrat(e.getChild(Contrat.class.getName())));
			}
		} catch (FileNotFoundException e){
			recovered = Printer.printError(this, "recovery", "File \""+path+"\" doesn't exist");
		} catch (IOException e) {
			recovered = Printer.printError(this, "recovery", "IOException\n\t"+e.toString());
		} catch (JDOMException e) {
			recovered = Printer.printError(this, "recovery", "JDOMException\n\tFile \""+path+"\" is empty");
			xmlFile.delete();
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
		String currentPublicKey = userManager.getCurrentUser().getKeys().getPublicKey().toString(16);
		AsymKeysImpl keys = userManager.getCurrentUser().getKeys().copy();
		keys.decryptPrivateKey(userManager.getCurrentUser().getClearPwd());
		// Recovery all local data in a new Manager
		Manager manager = new Manager(null);
		manager.recovery(path);
		
		if(path == null || path.isEmpty())
			path = VARIABLES.ManagerFilePath; 
		// Element Root
		Element root = new Element(Manager.class.getName());
		// ArrayList are used for adding data in local file.
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Item> items = this.userManager.getUserItems(currentPublicKey);
		ArrayList<UserMessage> messages = null; // TODO this.getUserMessages(currentPublicKey);
		ArrayList<Conversations> conversations = new ArrayList<Conversations>();
		ArrayList<Favorites> favorites = new ArrayList<Favorites>();
		HashMap<String,ArrayList<Contrat>> deals = new HashMap<String,ArrayList<Contrat>>();
		
		Conversations converC = this.messageManager.getUserConversations(currentPublicKey);
		if(converC!=null) conversations.add(converC);
		ArrayList<Contrat> arrayDealsC = this.contratManager.getDealsCurrentUser();
		if(arrayDealsC!=null) deals.put(currentPublicKey, arrayDealsC);
		Favorites favoC = this.favoriteManager.getFavoritesCurrentUser();
		if(favoC!=null) {
			favoC.encrypt(userManager.getCurrentUser().getClearPwd());
			favoC.sign(keys);
			favorites.add(favoC);
		}
		
		// Element users
		users.add(this.userManager.getCurrentUser());
		Element usersElement = new Element("users");
		usersElement.addContent(this.userManager.getCurrentUser().getRootElement());
		for (User user : manager.userManager.getUsers()){
			String userKey =  user.getKeys().getPublicKey().toString(16);
				if(!user.getKeys().getPublicKey().toString(16).equals(currentPublicKey)){
					usersElement.addContent(user.getRootElement());
					users.add(user);
					System.out.println("User added : \n"+user.toString());
				// Filling ArrayList items
				for (Item i : this.userManager.getUserItems(userKey)) {
					if(!items.contains(i))
						items.add(i);
				}
				for (Item i : manager.userManager.getUserItems(userKey)) {
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
				c = this.messageManager.getUserConversations(userKey);
				if(c != null && !conversations.contains(c))
					conversations.add(c);
				c = manager.messageManager.getUserConversations(userKey);
				if(c != null && !conversations.contains(c))
					conversations.add(c);
				// Filling ArrayList favorites
				Favorites f;
				f = this.favoriteManager.getUserFavorites(userKey);
				if(f != null && !favorites.contains(f)){
					favorites.add(f);
				}
				f = manager.favoriteManager.getUserFavorites(userKey);
				if(f != null && !favorites.contains(f))
					favorites.add(f);
				// Filling ArrayList deals
				if(!deals.containsKey(userKey))
					deals.put(userKey, new ArrayList<Contrat>());
				for (Contrat d : this.contratManager.getUserDeals(userKey)){
					if(!deals.get(userKey).contains(d))
						deals.get(userKey).add(d);
				}
				for (Contrat d : manager.contratManager.getUserDeals(userKey)){
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
		root.addContent(messagesElement);
		root.addContent(conversationsElement);
		root.addContent(favoritesElement);
		root.addContent(dealsElement);
		// Writing in file
		Document doc = new Document(root);
		XMLOutputter xmlOutput = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		xmlOutput.setFormat(format);
		try {
			xmlOutput.output(doc, new FileWriter(path));
		} catch (IOException e) {
			Printer.printError(this, "saving", "saving : "+e.toString());
		}  finally{
			Printer.printInfo(this, "saving", "Data saved localy");
		}
	}
	
	public static void main(String[] args){
		User u = new User("nick", "passWord", "name", "firstName", "email", "phone");
		Item i = new Item(u, "title", new Category("NA"), "description", "image", "country", "contact", 0L, 0L, TYPE.OFFER);
		Favorites f = new Favorites(u);
		f.addItem(i);
		f.sign(u.getKeys());
		f.encrypt("passWord");
		f.sign(u.getKeys());
		//System.out.println(f.toString());
		Favorites f2 = new Favorites(f.toString());
		System.out.println(f2.checkSignature(u.getKeys()));
	}
	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}
}

