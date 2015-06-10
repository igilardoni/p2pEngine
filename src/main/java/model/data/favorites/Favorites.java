package model.data.favorites;

import java.util.ArrayList;

import model.advertisement.AbstractAdvertisement;
import model.data.item.Item;
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
	private ArrayList<String> itemsKey;
	private ArrayList<byte[]> itemsKeyCrypted;
	
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
	public ArrayList<String> getItemsKey(){
		return itemsKey;
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
		if(itemsKey == null)
			itemsKey = new ArrayList<String>();
		return itemsKey.add(item.getItemKey());
	}
	private boolean addItem(String itemKey){
		if(itemKey == null || itemKey.isEmpty())
			return printError("addItem", "itemKey is null or empty");
		return itemsKey.add(itemKey);
	}
	public boolean addItemCrypted(byte[] b){
		if(b == null)
			return printError("addItemCrypted", "ItemByte empty");
		if(itemsKeyCrypted == null)
			itemsKeyCrypted = new ArrayList<byte[]>();
		if(itemsKeyCrypted.contains(b))
			return printError("addItemCrypted", "Crypted item already registed");
		return itemsKeyCrypted.add(b);
	}
	//////////////////////////////////////////////////// REMOVER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public boolean removeItem(Item item){
		if(item == null)
			return printError("removeItem", "Item empty");
		String itemKey = item.getItemKey();
		return itemsKey.remove(itemKey);
	}
	public boolean removeItem(String itemKey){
		if(itemKey == null || itemKey.isEmpty())
			return printError("removeItem", "itemKey null or empty");
		return itemsKey.remove(itemKey);
	}
	//////////////////////////////////////////////////// OTHERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void encrypt(String password){
		if(password==null || password.isEmpty()){
			printError("encrypt", "password null or empty");
			return;
		}
		itemsKeyCrypted = new ArrayList<byte[]>();
		Serpent s = new Serpent(password);
		for (String i : itemsKey) {
			itemsKeyCrypted.add(s.encrypt(i.getBytes()));
		}
		itemsKey = null;
		printInfo("encrypt", "Favorites items encrypted");
	}
	public void decrypt(String password){
		if(password==null || password.isEmpty()){
			printError("encrypt", "password null or empty");
			return;
		}
		itemsKey = new ArrayList<String>();
		Serpent s = new Serpent(password);
		if(password == null || password.isEmpty()){
			printError("decrypt", "password null or empty");
			return;
		}
		for(byte[] b : itemsKeyCrypted){
			String i = new String(s.decrypt(b));
			itemsKey.add(i);
		}
		itemsKeyCrypted = null;
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
	private String itemsKeyCryptedXML(){
		StringBuffer s = new StringBuffer();
		for(byte[] b : itemsKeyCrypted){
			s.append("<itemKey>");
			s.append(Hexa.bytesToHex(b));
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
			addItem(itemKey);
		}
	}
	private void loadItemsKeyCrypted(Element e){
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		if(itemsKeyCrypted == null)
			itemsKeyCrypted = new ArrayList<byte[]>();
		for(Element b : root.getChildren()) {
			byte[] itemByte = Hexa.hexToBytes(b.getValue());
			itemsKeyCrypted.add(itemByte);
		}
	}
	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	protected String getAdvertisementName() {
		return Favorites.class.getSimpleName();
	}
	@Override
	protected void setKeys() {
		itemsKey = null;
		itemsKeyCrypted = null;
		this.addKey("owner", false);
		this.addKey("itemsKeyCrypted", false);
		this.addKey("itemsKey", false);
	}
	@Override
	protected void putValues() {
		this.addValue("owner", this.getOwner());
		this.addValue("itemsKeyCrypted", itemsKeyCrypted==null?"":this.itemsKeyCryptedXML());
		this.addValue("itemsKey", itemsKey==null?"":this.itemsKeyXML());
	}
	@Override
	protected boolean handleElement(Element e) {
		String val = e.getText();
		switch(e.getName()) {
		case "owner":			this.setOwner(val);			break;
		case "itemsKey":		this.loadItemsKey(e);			break;
		case "itemsKeyCrypted":	this.loadItemsKeyCrypted(e);	break;
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
