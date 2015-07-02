package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Application;
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.user.Conversations;
import model.data.user.UserMessage;
import model.data.user.User;
import model.network.search.ItemSearcher;
import util.DateConverter;

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
		Application.getInstance().getManager().getUsers().registration(user);
	}
	/**
	 * Return true if, only if, login exists and password is good
	 * @param login
	 * @param password
	 * @return
	 */
	public static boolean login(String login, String password){
		if(Application.getInstance().getManager().getUsers().getCurrentUser()!=null)
			Application.getInstance().getManager().getUsers().logout();
		return Application.getInstance().getManager().getUsers().login(login, password);
	}
	/**
	 * Logout the current user
	 */
	public static void logout(){
		Application.getInstance().getManager().getUsers().logout();
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
		
	
		if(Application.getInstance().getManager().getUsers().getCurrentUser() == null){
			System.err.println(ManagerBridge.class.getName()+".addItem : No user logged !");
			return false;
		}
		if(Application.getInstance().getManager().getUsers().getCurrentUser().isPassword(oldPassword)){
			Application.getInstance().getManager().getUsers().getCurrentUser().setNick(nick);
			Application.getInstance().getManager().getUsers().getCurrentUser().setName(name);
			Application.getInstance().getManager().getUsers().getCurrentUser().setFirstName(firstName);
			Application.getInstance().getManager().getUsers().getCurrentUser().setEmail(email);
			Application.getInstance().getManager().getUsers().getCurrentUser().setPassWord(newPassword);
			Application.getInstance().getManager().getUsers().getCurrentUser().setClearPassword(newPassword);
			Application.getInstance().getManager().getUsers().getCurrentUser().setPhone(phone);
			Application.getInstance().getManager().getUsers().getCurrentUser().sign(Application.getInstance().getManager().getUsers().getCurrentUser().getKeys());
			Application.getInstance().getManager().getUsers().logout();
			return Application.getInstance().getManager().getUsers().login(nick, newPassword);
		}
		return false;
	}
	/**
	 * Get the currentUser, null if no user logged
	 * @return User currentUser
	 */
	public static User getCurrentUser() {
		return Application.getInstance().getManager().getUsers().getCurrentUser();
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
			System.err.println(ManagerBridge.class.getName()+".addItem : No user logged !");
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
		System.out.println(Application.getInstance().getManager().getUsers().getCurrentUser().getKeys().toString());
		Item item = new Item(Application.getInstance().getManager().getUsers().getCurrentUser(), title, c, description, image, country, contact, 0, l, t);
		item.sign(Application.getInstance().getManager().getUsers().getCurrentUser().getKeys());
		Application.getInstance().getManager().getItems().addItem(item, true);
		return item.getItemKey();
	}
	/**
	 * Remove item with title for the current User
	 * @param title
	 */
	public static void removeItem(String itemKey) {
		if(notLogged()){
			System.err.println(ManagerBridge.class.getName()+".removeItem : No user logged !");
			return;
		}
		Item item  = Application.getInstance().getManager().getItems().getItem(itemKey);
		if(item != null)
			Application.getInstance().getManager().getItems().removeItem(item);
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
			System.err.println(ManagerBridge.class.getName()+".updateItem : No user logged !");
			return;
		}
		Item item = new Item(Application.getInstance().getManager().getItems().getItem(itemKey).toString());
		item.setTitle(title);
		item.setCategory(new Category(category));
		item.setDescription(description);
		item.setImage(image);
		item.setCountry(country);
		item.setContact(contact);
		item.setLifeTime(Long.parseLong(lifeTime));
		item.setType(type.toUpperCase()=="WISH"?TYPE.DEMAND:TYPE.OFFER);
		item.sign(Application.getInstance().getManager().getUsers().getCurrentUser().getKeys());
		Application.getInstance().getManager().getItems().updateItem(itemKey, item);
	}
	/**
	 * Get user's items
	 * @param publicKey of the user
	 * @return ArrayList<Item> user's (who has publicKey) items 
	 */
	public static ArrayList<Item> getUserItems(String publicKey) {
		if(publicKey == null){
			System.err.println("public key empty");
			return null;
		}
		return Application.getInstance().getManager().getUsers().getUserItems(publicKey);
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
		return Application.getInstance().getManager().getItems().getItem(itemKey);
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
		Application.getInstance().getManager().addFavoritesItem(item);
	}
	/**
	 * Remove an item in current user's Favorite.
	 * @param itemKey - can be given with item.getItemKey()
	 */
	public static void removeFavoriteItem(String itemKey) {
		Application.getInstance().getManager().getFavoritesCurrentUser().removeItem(itemKey);
	}
	/**
	 * Return all item in current user's favorites
	 * @return
	 */
	public static ArrayList<String> getFavoriteItemsKey() {
		ArrayList<String> items =  Application.getInstance().getManager().getFavoritesCurrentUser().getItemsKey();
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
			Item iLocal = Application.getInstance().getManager().getItems().getItem(itemKey);
			if(iLocal!=null) items.add(iLocal);
			else{
				Item i = itemSearcher.search(itemKey);
				if(i!=null)
					items.add(i);
			}
		}
		return items;
	}
	
	public static Item getFavoriteItem(String itemKey){
		ItemSearcher itemSearcher = new ItemSearcher(Application.getInstance().getNetwork());
		Item iLocal = Application.getInstance().getManager().getItems().getItem(itemKey);
		if(iLocal != null) return iLocal;
		else return itemSearcher.search(itemKey);
	}
	////////////////////////////////////////////////// MESSAGES \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public static ArrayList<UserMessage> getMessages(){
		
		return null; // TODO getMESSAGE ? Application.getInstance().getManager().getUserMessages(getCurrentUser().getKeys().getPublicKey().toString(16));
		
	}
	public static ArrayList<UserMessage> getConversation(){
		ArrayList<UserMessage> messages = new ArrayList<UserMessage>();
		Conversations conversation = Application.getInstance().getManager().getCurrentUserConversations();
		conversation.unLock(getCurrentUser());
		for(String key : conversation.getSenders()){
			messages.addAll(conversation.getConversation(key));
		}
		return messages;
	}
	public static UserMessage getMessage(String id){
		ArrayList<UserMessage> messages = new ArrayList<UserMessage>();
		messages.addAll(getMessages());
		messages.addAll(getConversation());
		for (UserMessage message : messages) {
			if(message.getID().equals(id))
				return message;
		}
		return null;
	}
	//////////////////////////////////////////////////// OTHER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static boolean notLogged(){
		return Application.getInstance().getManager().getUsers().getCurrentUser() == null;
	}
}
