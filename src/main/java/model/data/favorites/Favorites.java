package model.data.favorites;

import java.util.ArrayList;

import model.advertisement.AbstractAdvertisement;
import model.data.deal.Deal;
import model.data.item.Category;
import model.data.item.Category.CATEGORY;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.user.User;

import org.jdom2.Element;

import util.StringToElement;
import util.secure.Serpent;

/**
 * This class can be instantiated for contains item favorite.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 * @author michael
 *
 */
public class Favorites extends AbstractAdvertisement{
	private String owner;
	private ArrayList<Item> items;
	private ArrayList<byte[]> itemsCrypted;
	
	///////////////////////////////////////////////// CONSTRUCTORS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Favorites(User owner){
		super();
		if(owner == null){
			printError("Favorites", "User empty");
			return;
		}
		if(owner.getKeys() == null || owner.getKeys().getPublicKey() == null){
			printError("Favorites", "User haven't publicKey");
			return;
		}
		this.setOwner(owner.getKeys().getPublicKey().toString(16));
	}
	public Favorites(Element e){
		super(e);
	}
	public Favorites(String XML){
		super(XML);
	}
	//////////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public String getOwner(){
		return owner;
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	public Item getItem(String itemKey){
		for (Item item : items) {
			if(item.getItemKey().equals(itemKey))
				return item;
		}
		return null;
	}
	public ArrayList<String> getItemsKey(){
		ArrayList<String> itemKeys = new ArrayList<String>();
		for (Item item : items) {
			itemKeys.add(item.getItemKey());
		}
		return itemKeys;
	}
	//////////////////////////////////////////////////// SETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void setOwner(String publicKey){
		if(publicKey == null || publicKey.isEmpty()){
			printError("setOwner", "publicKey is null or empty");
			return;
		}
		this.owner = publicKey;
	}
	public boolean addItem(Item item){
		if(item == null)
			return printError("addItem", "Item empty");
		if(item.getOwner() == null || item.getOwner().isEmpty())
			return printError("addItem", "Item haven't owner");
		if(items.contains(item)){
			if(items.get(items.indexOf(item)).getLastUpdated() < item.getLastUpdated())
				return items.remove(item) && items.add(item);
			return printError("addItem", "Item more recent already registed");
		}
		return items.add(item);
	}
	//////////////////////////////////////////////////// REMOVER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public boolean removeItem(Item item){
		if(item == null)
			return printError("removeItem", "Item empty");
		return items.remove(item);
	}
	public boolean removeItem(String itemKey){
		if(itemKey == null || itemKey.isEmpty())
			return printError("removeItem", "itemKey null or empty");
		for (Item item : items) {
			if(item.getItemKey().equals(itemKey))
				return removeItem(item);
		}
		return true;
	}
	//////////////////////////////////////////////////// OTHERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Item getAndRemoveItem(String itemKey){
		if(itemKey == null || itemKey.isEmpty()){
			printError("getAndRemoveItem", "itemKey null or empty");
			return null;
		}
		for (Item item : items) {
			if(item.getItemKey().equals(itemKey)){
				removeItem(item);
				return item;
			}
		}
		return null;
	}
	public void encrypt(String password){
		if(password==null || password.isEmpty()){
			printError("encrypt", "password null or empty");
			return;
		}
		Serpent s = new Serpent(password);
		for (Item i : items) {
			items.remove(i);
			itemsCrypted.add(s.encrypt(i.toString().getBytes()));
		}
	}
	public void decrypt(String password){
		if(password==null || password.isEmpty()){
			printError("encrypt", "password null or empty");
			return;
		}
		Serpent s = new Serpent(password);
		for(byte[] b : itemsCrypted){
			itemsCrypted.remove(b);
			Item i = new Item(new String(s.decrypt(b)));
			items.add(i);
		}
	}
	//////////////////////////////////////////////////// PRINTER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static boolean printError(String method, String error){
		System.err.println("ERROR : "+Deal.class.getName()+"."+method+" : "+error);
		return false;
	}
	////////////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private String itemsXML(){
		StringBuffer s = new StringBuffer();
		for(Item i: items) {
			s.append(i); 
		}
		return s.toString();
	}
	private void loadItems(Element e){
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element i: root.getChildren()) {
			addItem(new Item(i));
		}
	}
	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	protected String getAdvertisementName() {
		return Favorites.class.getSimpleName();
	}
	@Override
	protected void setKeys() {
		items = new ArrayList<Item>();
		itemsCrypted = new ArrayList<byte[]>();
		this.addKey("owner", false);
		this.addKey("items", false);
	}
	@Override
	protected void putValues() {
		this.addValue("owner", this.getOwner());
		this.addValue("items", this.itemsXML());
	}
	@Override
	protected boolean handleElement(Element e) {
		String val = e.getText();
		switch(e.getName()) {
		case "owner":	this.setOwner(val);		break;
		case "items":	this.loadItems(e);		break;
		}
		return false;
	}
	
	/////////////////////////////////////////////////// OVERRIDE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	public boolean equals(Object f){
		if(!(f instanceof Favorites))
			return false;
		Favorites favorites = (Favorites) f;
		if(!this.getOwner().equals(favorites.getOwner()))
			return false;
		return true;
	}
	
	public static void main(String[] args){
		User user = new User("nick", "passWord", "name", "firstName", "email", "phone");
		Item item1 = new Item(user, "title", new Category(CATEGORY.Appliances), "description", "image", "country", "contact", 0L, 0L, TYPE.PROPOSAL);
		System.out.println(item1);
		item1.sign(user.getKeys());
		System.out.println(item1.checkSignature(user.getKeys()));
		Serpent s = new Serpent(user.getClearPwd());
		byte[] crypt = s.encrypt(item1.toString().getBytes());
		byte[] decrypt = s.decrypt(crypt);
		Item item2 = new Item(new String(decrypt));
		System.out.println(item2);
		System.out.println(item2.checkSignature(user.getKeys()));
	}
}
