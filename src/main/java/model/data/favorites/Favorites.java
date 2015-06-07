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

import util.Hexa;
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
		if(items == null)
			items = new ArrayList<Item>();
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
		itemsCrypted = new ArrayList<byte[]>();
		Serpent s = new Serpent(password);
		for (Item i : items) {
			itemsCrypted.add(s.encrypt(i.toString().getBytes()));
		}
		items = null;
		printInfo("encrypt", "Favorites items encrypted");
	}
	public void decrypt(String password){
		if(password==null || password.isEmpty()){
			printError("encrypt", "password null or empty");
			return;
		}
		items = new ArrayList<Item>();
		Serpent s = new Serpent(password);
		if(password == null || password.isEmpty()){
			printError("decrypt", "password null or empty");
			return;
		}
		for(byte[] b : itemsCrypted){
			Item i = new Item(new String(s.decrypt(b)));
			items.add(i);
		}
		itemsCrypted = null;
		printInfo("decrypt", "Favorites items decrypted");
	}
	//////////////////////////////////////////////////// PRINTER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static boolean printError(String method, String error){
		System.err.println("ERROR : "+Deal.class.getName()+"."+method+" : "+error);
		return false;
	}
	private static void printInfo(String method, String info){
		System.out.println("INFO : "+Deal.class.getName()+"."+method+" : "+info);
	}
	////////////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private String itemsXML(){
		StringBuffer s = new StringBuffer();
		for(Item i: items) {
			s.append(i); 
		}
		return s.toString();
	}
	private String itemsCryptedXML(){
		StringBuffer s = new StringBuffer();
		for(byte[] b : itemsCrypted){
			s.append("<item>");
			s.append(Hexa.bytesToHex(b));
			s.append("</item>");
		}
		return s.toString();
	}
	private void loadItems(Element e){
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		items = new ArrayList<Item>();
		for(Element i: root.getChildren()) {
			addItem(new Item(i));
		}
	}
	private void loadItemsCrypted(Element e){
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		if(itemsCrypted == null)
			itemsCrypted = new ArrayList<byte[]>();
		for(Element b : root.getChildren()) {
			byte[] itemByte = Hexa.hexToBytes(b.getValue());
			itemsCrypted.add(itemByte);
		}
	}
	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	protected String getAdvertisementName() {
		return Favorites.class.getSimpleName();
	}
	@Override
	protected void setKeys() {
		items = null;
		itemsCrypted = null;
		this.addKey("owner", false);
		this.addKey("itemsCrypted", false);
		this.addKey("items", false);
	}
	@Override
	protected void putValues() {
		this.addValue("owner", this.getOwner());
		this.addValue("itemsCrypted", itemsCrypted==null?"":this.itemsCryptedXML());
		this.addValue("items", items==null?"":this.itemsXML());
	}
	@Override
	protected boolean handleElement(Element e) {
		String val = e.getText();
		switch(e.getName()) {
		case "owner":			this.setOwner(val);			break;
		case "items":			this.loadItems(e);			break;
		case "itemsCrypted":	this.loadItemsCrypted(e);	break;
		default: return false;
		}
		return true;
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
}
