package model.data.favorites;

import java.util.ArrayList;

import model.data.deal.Deal;
import model.data.item.Item;
import model.data.user.User;

/**
 * This class can be instantiated for contains item favorite.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 * @author michael
 *
 */
public class Favorites {
	private String owner;
	private ArrayList<Item> items = new ArrayList<Item>();
	//private ArrayList<String> usersPublicKey = new ArrayList<String>(); Useless ?!?
	
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
	/*public ArrayList<String> getUsers(){
		return usersPublicKey;
	}*/
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
		/*if(!usersPublicKey.contains(item.getOwner()))
			return printError("addItem", "Owner unknown");*/
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
	//////////////////////////////////////////////////// OTHERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//////////////////////////////////////////////////// PRINTER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static boolean printError(String method, String error){
		System.err.println(Deal.class.getName()+"."+method+" : "+error);
		return false;
	}
	////////////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}
