package model.data.manager;

import java.io.IOException;
import java.util.ArrayList;

import net.jxta.discovery.DiscoveryService;

import org.jdom2.Element;

import util.Printer;
import util.StringToElement;
import model.data.item.Item;

public class ItemManager {
	private ArrayList<Item> items = new ArrayList<Item>();			// list of items handled by this manager.
	private Manager manager;
	
	
	
	public ItemManager(Manager m) {
		manager = m;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	/**
	 * Use to found a item with itemKey
	 * @param itemKey
	 * @return
	 */
	public Item getItem(String itemKey){
		if(itemKey == null || itemKey.isEmpty()){
			Printer.printError(this, "getItem", ".getItem : itemKey is empty or null !");
			return null;
		}
		for (Item item : items) {
			if(item.getItemKey().equals(itemKey))
				return item;
		}
		return null;
	}
	
	/**
	 * Get an XML string representing all the items that are saved on this device.
	 * @return A string, XML formated
	 */
	protected String getItemsXML() {
		StringBuffer s = new StringBuffer();
		for(Item i: items) {
			s.append(i); 
		}
		return s.toString();
	}
	
	/**
	 * Load all the items in this element
	 * @param e an element that contains items in XML format.
	 * TODO a voir
	 */
	protected void loadItems(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element i: root.getChildren()) {
			addItem(new Item(i));
		}
	}
	
	/**
	 * to remove all items with lifeTime is over
	 */
	public void cleanItems(){
		for(int i = 0; i <items.size();i++){
			if(!items.get(i).isAlive(manager.getUsers().whoHas(items.get(i)).getLastUpdated()
					))
				removeItem(items.get(i));
		}
	}
	
	/**
	 * to add a item in this instance of manager
	 * if owner of the item isn't registered in this instance of manger, function will fail
	 * @param i - Item to add
	 */
	public void addItem(Item i){
		if(i == null){
			Printer.printError(this, "addItem","This Item is empty !");
			return;
		}
		String owner = i.getOwner();
		if(owner == null || owner.isEmpty()){
			Printer.printError(this, "addItem","No owner found !");
			return;
		}
		if(!manager.getUsers().userExists(owner)){
			Printer.printError(this, "addItem","Owner unknown for "+i.getTitle());
			return;
		}
		if(owner.equals(manager.getUsers().getCurrentUser())){
			if(!i.checkSignature(manager.getUsers().getCurrentUser().getKeys())){
				Printer.printError(this, "addItem","Bad Signature for "+i.getTitle());
				return;
			}
		}else{
			if(!i.checkSignature(manager.getUsers().getUser(owner).getKeys())){
				Printer.printError(this, "addItem","Bad Signature for "+i.getTitle());
				return;
			}
		}
		if(items.contains(i)){
			if(items.get(items.indexOf(i)).getLastUpdated() >= i.getLastUpdated()){
				Printer.printError(this, "addItem","Item "+i.getTitle()+" is already registred !");
				return;
			}else{
				items.remove(i);
			}
		}
		items.add(i);
	}
	
	/**
	 * Add an item
	 * @param i an Item, must be signed.
	 * @param publish if true the item is immedialty published on network.
	 */
	public void addItem(Item i, boolean publish) {
		addItem(i);
		if(publish) {
			try {
				manager.getNetwork().getGroup("items").getDiscoveryService().publish(i);
				manager.getNetwork().getGroup("items").getDiscoveryService().remotePublish(i);
				System.out.println("item publie :");
				System.out.println(i);
			} catch (IOException e) {
				Printer.printError(this, "addItem",e.toString());
			}
		}
	}
	
	/**
	 * TODO EXPLICATION
	 * @param itemKey
	 * @param item
	 */
	public void updateItem(String itemKey, Item item){
		removeItem(getItem(itemKey));
		addItem(item);
	}
	
	/**
	 * Remove an item from the Manager
	 * @param item
	 * @return
	 */
	public boolean removeItem(Item item){
		return items.remove(item);
	}
	
	protected void publishItems() {
		DiscoveryService discovery = manager.getNetwork().getGroup("items").getDiscoveryService();
		for(Item i: items) {
			try {
				discovery.flushAdvertisement(i);
				discovery.publish(i); //"i have this item"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
