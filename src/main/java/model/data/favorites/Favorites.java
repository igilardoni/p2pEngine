package model.data.favorites;

import java.util.ArrayList;

import model.advertisement.AbstractAdvertisement;
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.user.User;

import org.jdom2.Element;

import util.Hexa;
import util.StringToElement;
import util.secure.AsymKeysImpl;
import util.secure.Serpent;

/**
 * This class can be instantiated for contains item favorite.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 * @author michael
 *
 */
public class Favorites extends AbstractAdvertisement{
	private ArrayList<String> itemsKey;
	private boolean crypted;
	
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
		this.setKeys(owner.getKeys());
	}
	public Favorites(Element e){
		super(e);
	}
	public Favorites(String XML){
		super(XML);
	}
	//////////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public String getOwner(){
		return getKeys().getPublicKey().toString(16);
	}
	public ArrayList<String> getItemsKey(){
		return itemsKey;
	}
	public boolean isCrypted(){
		return crypted;
	}
	//////////////////////////////////////////////////// SETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public boolean addItem(Item item){
		if(item == null)
			return printError("addItem", "Item empty");
		if(item.getOwner() == null || item.getOwner().isEmpty())
			return printError("addItem", "Item haven't owner");
		if(itemsKey == null)
			itemsKey = new ArrayList<String>();
		if(crypted)
			return printError("addItem", "Favorites is encrypted");
		return itemsKey.add(item.getItemKey());
	}
	public boolean addItem(String itemKey){
		if(itemKey == null || itemKey.isEmpty())
			return printError("addItem", "itemKey is null or empty");
		if(crypted)
			return printError("addItem", "Favorites is encrypted");
		return itemsKey.add(itemKey);
	}
	public void setCrypted(boolean crypted){
		this.crypted = crypted;
	}
	//////////////////////////////////////////////////// REMOVER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public boolean removeItem(Item item){
		if(item == null)
			return printError("removeItem", "Item empty");
		if(crypted)
			return printError("removeItem", "Favorites is encrypted");
		String itemKey = item.getItemKey();
		return itemsKey.remove(itemKey);
	}
	public boolean removeItem(String itemKey){
		if(itemKey == null || itemKey.isEmpty())
			return printError("removeItem", "itemKey null or empty");
		if(crypted)
			return printError("removeItem", "Favorites is encrypted");
		return itemsKey.remove(itemKey);
	}
	//////////////////////////////////////////////////// OTHERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void encrypt(String password){
		if(password==null || password.isEmpty()){
			printError("encrypt", "password null or empty");
			return;
		}
		if(crypted){
			printError("encrypt", "Favorites already encrypted");
			return;
		}
		ArrayList<String> itemsKeyCrypted = new ArrayList<String>();
		Serpent s = new Serpent(password);
		for (String i : itemsKey) {
			itemsKeyCrypted.add(Hexa.bytesToHex(s.encrypt(i.getBytes())));
		}
		itemsKey = new ArrayList<String>();
		itemsKey.addAll(itemsKeyCrypted);
		crypted = true;
		printInfo("encrypt", "Favorites items encrypted");
	}
	public void decrypt(String password){
		if(password==null || password.isEmpty()){
			printError("encrypt", "password null or empty");
			return;
		}
		if(!crypted){
			printError("decrypt", "Favorites already decrypted");
			return;
		}
		Serpent s = new Serpent(password);
		if(password == null || password.isEmpty()){
			printError("decrypt", "password null or empty");
			return;
		}
		ArrayList<String> itemsKeyCrypted = new ArrayList<String>();
		itemsKeyCrypted.addAll(itemsKey);
		itemsKey = new ArrayList<String>();
		for(String b : itemsKeyCrypted){
			String i = new String(s.decrypt(Hexa.hexToBytes(b)));
			itemsKey.add(i);
		}
		itemsKeyCrypted = null;
		crypted = false;
		printInfo("decrypt", "Favorites items decrypted");
	}
	//////////////////////////////////////////////////// PRINTER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static boolean printError(String method, String error){
		System.err.println("ERROR : "+Favorites.class.getName()+"."+method+" : "+error);
		return false;
	}
	private static void printInfo(String method, String info){
		System.out.println("INFO : "+Favorites.class.getName()+"."+method+" : "+info);
	}
	////////////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private String itemsKeyXML(){
		StringBuffer s = new StringBuffer();
		for(String i: itemsKey) {
			s.append("<itemKey>");
			s.append(i);
			s.append("</itemKey>");
		}
		return s.toString();
	}
	private void loadItemsKey(Element e){
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		if(itemsKey == null)
			itemsKey = new ArrayList<String>();
		for(Element i: root.getChildren()) {
			String itemKey = new String(i.getValue());
			itemsKey.add(itemKey);
		}
	}
	private void loadCrypted(String val){
		if(val.toLowerCase().equals("true"))
			this.setCrypted(true);
	}
	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	protected String getAdvertisementName() {
		return Favorites.class.getSimpleName();
	}
	@Override
	protected void setKeys() {
		itemsKey = new ArrayList<String>();
		crypted = false;
		
		this.addKey("itemsKey", false, true);
		this.addKey("crypted", false, false);
	}
	@Override
	protected void putValues() {
		this.addValue("itemsKey", this.itemsKeyXML());
		this.addValue("crypted", String.valueOf(crypted));
	}
	@Override
	protected boolean handleElement(Element e) {
		String val = e.getText();
		switch(e.getName()) {
		case "itemsKey":		this.loadItemsKey(e);						break;
		case "crypted":			this.loadCrypted(val);						break;
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
	
	public static void main(String[] args){
		User user = new User("nick", "passWord", "name", "firstName", "email", "phone");
		Item item = new Item(user, "title", new Category("NA"), "description", "image", "country", "contact", 0L, 0L, TYPE.OFFER);
		Favorites f = new Favorites(user);
		f.addItem(item);
		f.sign(user.getKeys());
		System.out.println(f.checkSignature(user.getKeys()));
	}
}
