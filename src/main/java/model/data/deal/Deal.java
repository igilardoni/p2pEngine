package model.data.deal;

import java.util.ArrayList;
import java.util.HashMap;

import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;
import model.data.item.Item;
import model.data.user.User;

/**
 * This class can be instantiated for contains an agreement.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 * @author michael
 *
 */
public class Deal extends AbstractAdvertisement {
	private static final String[] stringState = {"draft", "waiting", "signed", "to sign"};
	
	private String title;			// Title of deal
	private int state = 0;			// State of deal (draft at start)
	private ArrayList<String> signatories = new ArrayList<String>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private HashMap<Item, String> itemFor = new HashMap<Item, String>();
	private ArrayList<Layout> layouts = new ArrayList<Layout>();
	// TODO add proofs of signature and signature
	
	//////////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public String getTitle(){
		return title;
	}
	public ArrayList<String> getSignatories(){
		return signatories;
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	public String getRecipientOf(Item item){
		return itemFor.containsKey(item)?itemFor.get(item):null;
	}
	public ArrayList<Item> getItemsReceivedBy(String publicKey){
		ArrayList<Item> is = new ArrayList<Item>();
		for (Item item : items) {
			if(itemFor.containsKey(item) && itemFor.get(item).equals(publicKey))
				is.add(item);
		}
		return is;
	}
	public String getStateStringFormat(){
		if(state < 0 || state > stringState.length -1){
			printError("getStateStringFormat","Unknown state !");
			return "unknown state";
		}
		return stringState[state];
	}
	public int getState(){
		if(state < 0 || state >= stringState.length){
			printError("getState","Unknown state !");
			return -1;
		}
		return state;
	}
	public boolean isDraft(){
		return state == 0;
	}
	public boolean isWaiting(){
		return state == 1;
	}
	public boolean isSigned(){
		return state == 2;
	}
	public boolean isToSign(){
		return state == 3;
	}
	public ArrayList<Layout> getLayout(){
		return layouts;
	}
	
	///////////////////////////////////////////////// STATIC GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public static String[] getPossiblesStates(){
		return stringState;
	}
	public static String getPossibleState(int i){
		if(i < 0 || i > stringState.length -1){
			printError("getPossibleState","Unknown state !");
			return "unknown state";
		}
		return stringState[i];
	}
	
	//////////////////////////////////////////////////// SETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void setTitle(String title){
		if(!isDraft()){
			printError("setTitle", "This Deal isn't a draft");
			return;
		}
		this.title = title;
	}
	private void setState(int state){
		if(state < 0 || state >= stringState.length){
			printError(".setState", "Unknown state !");
			return;
		}
		this.state = state;
	}
	public void draft(){
		this.state = 0;
		// TODO Maybe delete proofs received
	}
	public boolean addSignatory(String publicKey){
		if(!isDraft())
			return printError("addSignatory", "This Deal isn't a draft");
		if(publicKey == null || publicKey.isEmpty())
			return printError("addSignatory", "publicKey Empty !");
		return signatories.add(publicKey);
	}
	public boolean addSignatory(User user){
		if(!isDraft())
			return printError("addSignatory", "This Deal isn't a draft");
		if(user == null)
			return printError("addSignatory", "User empty");
		if(user.getKeys() == null || user.getKeys().getPublicKey() == null)
			return printError("addSignatory", "User have to got PublicKey !");
		return addSignatory(user.getKeys().getPublicKey().toString(16));
	}
	public boolean addItem(Item item){
		if(!isDraft())
			return printError("addItem", "This Deal isn't a draft");
		if(item == null)
			return printError("addItem", "Item empty");
		if(item.getOwner() == null || item.getOwner().isEmpty())
			return printError("addItem", "Item "+item.getTitle()+" doesn't have Owner");
		String owner = item.getOwner();
		if(signatories.contains(owner))
			return signatories.add(owner) && items.add(item);
		return items.add(item);
	}
	public boolean addTransferRule(Item item, String publicKey){
		if(!isDraft())
			return printError("addTransferRule", "This Deal isn't a draft");
		if(item == null || publicKey == null || publicKey.isEmpty())
			return printError("addTransferRule", "Item and PublicKey haven't to be empty");
		if(!signatories.contains(publicKey))
			if(!addSignatory(publicKey))
				return printError("addTransferRule", "Impossible to add publicKey");
		if(!items.contains(item))
			if(!addItem(item))
				return printError("addTransferRule", "Impossible to add Item");
		if(itemFor.containsKey(item)){
			printError("addTransferRule", "Rule for Item "+item.getTitle()+" deleted");
			itemFor.remove(item);
		}
		return itemFor.put(item, publicKey) != null;
	}
	public boolean addTransferRule(Item item, User user){
		if(!isDraft())
			return printError("addTransferRule", "This Deal isn't a draft");
		if(user == null)
			return printError("addTransferRule", "User empty");
		if(user.getKeys() == null || user.getKeys().getPublicKey() == null)
			return printError("addTransferRule", "User haven't publicKey");
		return addTransferRule(item, user.getKeys().getPublicKey().toString(16));
	}

	//////////////////////////////////////////////////// PRINTER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	private static boolean printError(String method, String error){
		System.err.println(Deal.class.getName()+"."+method+" : "+error);
		return false;
	}
	
	public String toPrint(){
		return "";
	}
	///////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	protected String getAdvertisementName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void setKeys() {
		// TODO Auto-generated method stub
	}
	@Override
	protected void putValues() {
		// TODO Auto-generated method stub
	}
	@Override
	protected boolean handleElement(Element e) {
		// TODO Auto-generated method stub
		return false;
	}
	///////////////////////////////////////////////////// OTHER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}
