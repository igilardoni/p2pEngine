package model.data.favorites;

import java.util.ArrayList;

import org.jdom2.Element;

import util.StringToElement;
import model.advertisement.AbstractAdvertisement;
import model.data.deal.Deal;
import model.data.item.Item;
import model.data.user.User;

/**
 * This class can be instantiated for contains item favorite.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 * @author michael
 *
 */
public class Favorites extends AbstractAdvertisement{
	private String owner;
	private ArrayList<Item> items;
	
	///////////////////////////////////////////////// CONSTRUCTORS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Favorites(String owner){
		super();
		this.setOwner(owner);
	}
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
	//////////////////////////////////////////////////// PRINTER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static boolean printError(String method, String error){
		System.err.println(Deal.class.getName()+"."+method+" : "+error);
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
}
