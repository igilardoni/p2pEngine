package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Application;
import model.data.contrat.Clause;
import model.data.contrat.Contrat;
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.manager.UserManager;
import model.data.user.Conversations;
import model.data.user.UserMessage;
import model.data.user.User;
import model.network.search.ItemSearcher;
import util.DateConverter;
import util.Printer;
import util.secure.AsymKeysImpl;

public class ManagerBridge{
	private ManagerBridge(){
	}
	//////////////////////////////////////////////////// USERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Add NEW User to current Manager
	 * @param nick
	 * @param password
	 * @param name
	 * @param firstName
	 * @param login
	 * @param login
	 */
	public static void registration(String nick,String password, String name, String firstName, String email, String phone){
		User user = new User(nick, password, name, firstName, email, phone);
		user.setClearPassword(password);
		Application.getInstance().getManager().getUserManager().registration(user);
	}
	/**
	 * Return true if, only if, login exists and password is good
	 * @param login
	 * @param password
	 * @return
	 */
	public static boolean login(String login, String password){
		if(Application.getInstance().getManager().getUserManager().getCurrentUser()!=null)
			Application.getInstance().getManager().getUserManager().logout();
		return Application.getInstance().getManager().getUserManager().login(login, password);
	}
	/**
	 * Logout the current user
	 */
	public static void logout(){
		Application.getInstance().getManager().getUserManager().logout();
	}
	/**
	 * Update the current account
	 * @param nick
	 * @param oldPassword
	 * @param newPassword
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 * @return
	 */
	public static boolean updateAccount(String nick, String oldPassword, String newPassword,
			String name, String firstName, String email, String phone){
		
	
		if(Application.getInstance().getManager().getUserManager().getCurrentUser() == null){
			return Printer.printError(ManagerBridge.class, "updateAccount", "No user logged !");
		}
		if(Application.getInstance().getManager().getUserManager().getCurrentUser().isPassword(oldPassword)){
			Application.getInstance().getManager().getUserManager().getCurrentUser().setNick(nick);
			Application.getInstance().getManager().getUserManager().getCurrentUser().setName(name);
			Application.getInstance().getManager().getUserManager().getCurrentUser().setFirstName(firstName);
			Application.getInstance().getManager().getUserManager().getCurrentUser().setEmail(email);
			Application.getInstance().getManager().getUserManager().getCurrentUser().setPassWord(newPassword);
			Application.getInstance().getManager().getUserManager().getCurrentUser().setClearPassword(newPassword);
			Application.getInstance().getManager().getUserManager().getCurrentUser().setPhone(phone);
			Application.getInstance().getManager().getUserManager().getCurrentUser().sign(Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
			Application.getInstance().getManager().getUserManager().logout();
			return Application.getInstance().getManager().getUserManager().login(nick, newPassword);
		}
		return false;
	}
	/**
	 * Get the currentUser, null if no user logged
	 * @return User currentUser
	 */
	public static User getCurrentUser() {
		return Application.getInstance().getManager().getUserManager().getCurrentUser();
	}
	//////////////////////////////////////////////////// ITEMS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\	
	/**
	 * Add an current user's item in the manager 
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param country
	 * @param contact
	 * @param lifeTime
	 * @param type
	 */
	public static String addItem(String title, String category, String description, String image, String country, String contact, String lifeTime, String type ){
		if(notLogged()){
			Printer.printError(ManagerBridge.class, "addItem", "No user logged !");
			return null;
		}
		Long l = DateConverter.getLongBefore(lifeTime);
		l = l>0?l:0L;
		Category c = new Category(category);
		Item.TYPE t;
		switch(type.toUpperCase()){
		case "WISH":
			t = TYPE.DEMAND;
			break;
		case "PROPOSAL":
			t = TYPE.OFFER;
			break;
		default:
			t = TYPE.OFFER;
		}
		Item item = new Item(Application.getInstance().getManager().getUserManager().getCurrentUser(), title, c, description, image, country, contact, 0, l, t);
		AsymKeysImpl keys = new AsymKeysImpl(Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys().toString());
		keys.decryptPrivateKey(Application.getInstance().getManager().getUserManager().getCurrentUser().getClearPwd());
		item.sign(keys);
		Application.getInstance().getManager().getItemManager().addItem(item, true);
		return item.getItemKey();
	}
	/**
	 * Remove item with title for the current User
	 * @param title
	 */
	public static void removeItem(String itemKey) {
		if(notLogged()){
			Printer.printError(ManagerBridge.class, "removeItem", "No user logged !");
			return;
		}
		Item item  = Application.getInstance().getManager().getItemManager().getItem(itemKey);
		if(item != null)
			Application.getInstance().getManager().getItemManager().removeItem(item);
	}
	/**
	 * Update item with title for the current user
	 * Care, the title can't be changed !
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param country
	 * @param contact
	 * @param lifeTime
	 * @param type
	 */
	public static void updateItem(String itemKey, String title, String category,
			String description, String image, String country,
			String contact, String lifeTime, String type) {
		if(notLogged()){
			Printer.printError(ManagerBridge.class, "updateItem", "No user logged !");
			return;
		}
		AsymKeysImpl key = Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys().copy();
		key.decryptPrivateKey(Application.getInstance().getManager().getUserManager().getCurrentUser().getClearPwd());
		
		Item item = new Item(Application.getInstance().getManager().getItemManager().getItem(itemKey).toString());
		item.setTitle(title);
		item.setCategory(new Category(category));
		item.setDescription(description);
		item.setImage(image);
		item.setCountry(country);
		item.setContact(contact);
		item.setLifeTime(DateConverter.getLong(lifeTime)-item.getDate());
		item.setType(type.toUpperCase()=="WISH"?TYPE.DEMAND:TYPE.OFFER);
		item.sign(key);
		Application.getInstance().getManager().getItemManager().updateItem(itemKey, item);
	}
	/**
	 * Get user's items
	 * @param publicKey of the user
	 * @return ArrayList<Item> user's (who has publicKey) items 
	 */
	public static ArrayList<Item> getUserItems(String publicKey) {
		if(publicKey == null){
			Printer.printError(ManagerBridge.class, "getUserItems", "public key empty");
			return null;
		}
		return Application.getInstance().getManager().getUserManager().getUserItems(publicKey);
	}
	/**
	 * Get current user's items
	 * @return ArrayList<Item> current user's items 
	 */
	public static ArrayList<Item> getCurrentUserItems() {
		return getUserItems(getCurrentUser().getKeys().getPublicKey().toString(16));
	}
	/**
	 * Get current user's item's itemKey
	 * @param itemKey
	 * @return
	 */
	public static Item getCurrentUserItem(String itemKey){
		if(itemKey == null){
			Printer.printError(ManagerBridge.class, "getCurrentUserItem", "item key empty");
			return null;
		}
		return Application.getInstance().getManager().getItemManager().getItem(itemKey);
	}
	/**
	 * Get Searchable fields for Item
	 * @return
	 */
	public static ArrayList<String> getItemSearchableFields(){
		ArrayList<String> fields = new ArrayList<String>();
		for(String s : (new Item()).getIndexFields()){
			fields.add(s);
		}
		return fields;
	}
	/**
	 * Can be used for sort current user's items with itemKey as key
	 * CAREFUL very resource intensive
	 * @return
	 */
	@SuppressWarnings("unused")
	private static HashMap<String, Item> getCurrentUserClassedItems(){
		HashMap<String, Item> hashmap = new HashMap<String, Item>();
		for(Item i : getCurrentUserItems()){
			String key = i.getItemKey();
			hashmap.put(key, i);
		}
		return hashmap;
	}
	////////////////////////////////////////////////// FAVORITES \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Use to add an item in current user's Favorites
	 * @param item
	 */
	public static void addFavoriteItem(Item item) {
		Application.getInstance().getManager().getFavoriteManager().addFavoritesItem(item);
	}
	/**
	 * Remove an item in current user's Favorite.
	 * @param itemKey - can be given with item.getItemKey()
	 */
	public static void removeFavoriteItem(String itemKey) {
		Application.getInstance().getManager().getFavoriteManager().getFavoritesCurrentUser().removeItem(itemKey);
	}
	/**
	 * Return all item in current user's favorites
	 * @return
	 */
	public static ArrayList<String> getFavoriteItemsKey() {
		ArrayList<String> items =  Application.getInstance().getManager().getFavoriteManager().getFavoritesCurrentUser().getItemsKey();
		return items==null?(new ArrayList<String>()):items;
	}
	/**
	 * Search on network all items' itemKey in Favorites
	 * @return
	 */
	public static ArrayList<Item> getFavoriteItems(){
		ArrayList<Item> items = new ArrayList<Item>();
		ItemSearcher itemSearcher = new ItemSearcher(Application.getInstance().getNetwork());
		for(String itemKey : getFavoriteItemsKey()){
			Item iLocal = Application.getInstance().getManager().getItemManager().getItem(itemKey);
			if(iLocal!=null) items.add(iLocal);
			else{
				Item i = itemSearcher.search(itemKey);
				if(i!=null)
					items.add(i);
				else
					Application.getInstance().getManager().getFavoriteManager().getFavoritesCurrentUser().removeItem(itemKey);
			}
		}
		return items;
	}
	
	public static Item getFavoriteItem(String itemKey){
		ItemSearcher itemSearcher = new ItemSearcher(Application.getInstance().getNetwork());
		Item iLocal = Application.getInstance().getManager().getItemManager().getItem(itemKey);
		if(iLocal != null) return iLocal;
		else return itemSearcher.search(itemKey);
	}
	////////////////////////////////////////////////// MESSAGES \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public static Conversations getCurrentUserConversation() {
		return Application.getInstance().getManager().getMessageManager().getCurrentUserConversations();
	}
	public static ArrayList<UserMessage> getCurrentUserMessages(){
		ArrayList<UserMessage> messages = new ArrayList<UserMessage>();
		Conversations conversation = Application.getInstance().getManager().getMessageManager().getCurrentUserConversations();
		conversation.unLock(getCurrentUser());
		for(String key : conversation.getSenders()){
			messages.addAll(conversation.getConversation(key));
		}
		return messages;
	}
	public static UserMessage getMessage(String key) {
		ArrayList<UserMessage> messages = getCurrentUserMessages();
		for (UserMessage m : messages) {
			if(m.getId().equals(key)){
				m.setRead(true);
				m.sign(getCurrentUser().getKeys());
				getCurrentUserConversation().sign(getCurrentUser().getKeys());
				return m;
			}
		}
		return null;
	}
	public static boolean removeMessage(String publicKey, String id) {
		ArrayList<UserMessage> messages = getCurrentUserConversation().getConversation(publicKey);
		for(UserMessage m : messages) {
			if(m.getId().equals(id)) {
				if(getCurrentUserConversation().getConversation(publicKey).remove(m)){
					if(getCurrentUserConversation().getConversation(publicKey).size() <= 0)
						removeConversation(publicKey);
					return true;
				}
			}
		}
		return false;
	}
	public static boolean removeConversation(String publicKey) {
		return Application.getInstance().getManager().getMessageManager().removeCurrentUserConversation(publicKey);
	}
	/////////////////////////////////////////////////// CONTRAT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public static Contrat createContrat(String title){
		Contrat contrat = Application.getInstance().getManager().getContratManager().newDeal(title);
		return contrat;
	}
	public static boolean addItemContrat(Item item, String contratID){
		return Application.getInstance().getManager().getContratManager().addItem(contratID, item);
	}
	public static boolean removeContrat(String contratID) {
		return Application.getInstance().getManager().getContratManager().removeContrat(Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys().getPublicKey().toString(16), contratID);
	}
	public static ArrayList<Contrat> getCurrentUserContrats(){
		return Application.getInstance().getManager().getContratManager().getDealsCurrentUser();
	}
	public static Contrat getCurrentUserContrat(String id){
		ArrayList<Contrat> contrats = getCurrentUserContrats();
		for (Contrat contrat : contrats) {
			if(contrat.getId().equals(id))
				return contrat;
		}
		return null;
	}
	public static boolean removeItemContrat(String itemKey, String contratID){
		return Application.getInstance().getManager().getContratManager().removeItemContrat(itemKey, contratID);
	}
	public static boolean removeSignatoryContrat(String publicKey, String contratID){
		Application.getInstance().getManager().getContratManager().removeSignatoryContrat(contratID, publicKey);
		return false;
	}
	public static boolean renameContrat(String contratID, String title) {
		if(Application.getInstance().getManager().getContratManager().getContract(contratID) == null)
			return false;
		Application.getInstance().getManager().getContratManager().getContract(contratID).setTitle(title);
		return true;
	}
	public static boolean changeRuleContrat(String contratID, String itemKey, String from, String to) {
		Contrat c = Application.getInstance().getManager().getContratManager().getContract(contratID);
		if(c == null)
			return false;
		return c.addTransferRule(itemKey, to);
	}
	public static Clause addClause(String contratID) throws Exception {
		Clause layout = new Clause();
		if(Application.getInstance().getManager().getContratManager().getContract(contratID).addClaus(layout))
			return layout;
		else
			throw new Exception("Clause not added");
	}
	public static boolean saveClause(String contratID, String id, String title, String content) {
		if(Application.getInstance().getManager().getContratManager().getContract(contratID).getClause(id) == null)
			return false;
		Application.getInstance().getManager().getContratManager().getContract(contratID).getClause(id).setTitle(title);
		Application.getInstance().getManager().getContratManager().getContract(contratID).getClause(id).setContent(content);
		return true;
	}
	public static boolean removeClause(String contratID, String id) {
		return Application.getInstance().getManager().getContratManager().getContract(contratID).removeClause(id);
	}
	//////////////////////////////////////////////////// OTHER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static boolean notLogged(){
		return Application.getInstance().getManager().getUserManager().getCurrentUser() == null;
	}
}
