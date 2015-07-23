package model.data.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jdom2.Element;

import util.Printer;
import util.StringToElement;
import model.data.contrat.Contrat;
import model.data.item.Item;
import model.data.user.User;


/**
 * Manager for deals.
 * @author Julien Prudhomme
 * @author Michael Dubuis
 *
 */
public class ContratManager {
	private HashMap<String, ArrayList<Contrat>> deals = new HashMap<String, ArrayList<Contrat>>();
	private Manager manager;
	
	
    ///////////////////////////////////////////////// CONSTRUCTORS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public ContratManager(Manager m) {
		manager = m;
	}
	
    ///////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public HashMap<String, ArrayList<Contrat>> getDeals() {
		return deals;
	}
	
	/**
	 * Get the current user's deals. If doesn't exist, return create new ArrayList;
	 * @return ArrayList<Deal>
	 */
	public ArrayList<Contrat> getUserDeals(String publicKey){
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		return deals.get(publicKey);
	}
	
	/**
	 * Get the current user's deals. If doesn't exist, it will be created
	 * @return ArrayList<Deal>
	 */
	public ArrayList<Contrat> getDealsCurrentUser(){
		User currentUser = manager.getUserManager().getCurrentUser();
		if(currentUser == null) {
			System.err.println("no user logged");
			return null;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		return getUserDeals(publicKey);
	}
	
	public boolean containsUser(String user) {
		return deals.containsKey(user);
	}
	
	///////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Get an XML string representing all the deals that are saved on this device.
	 * @return A string, XML formated
	 */
	protected String getDealsXML(){
		StringBuffer s = new StringBuffer();
		for(Entry<String, ArrayList<Contrat>> entry : this.deals.entrySet()) {
			String owner = entry.getKey();
			ArrayList<Contrat> deals = entry.getValue();
			for (Contrat d : deals) {
				s.append("<deal>");
				s.append("<owner>");
				s.append(owner);
				s.append("</owner>");
				s.append(d);
				s.append("</deal>");
			}
		}
		return s.toString();
	}
	
	/**
	 * Load all the deals in this element
	 * @param e an element that contains messages in XML format.
	 */
	protected void loadDeals(Element e){
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element d: root.getChildren()){
			String owner = d.getChildText("owner");
			Element deal = d.getChild("Deal");
			addDeal(owner, new Contrat(deal));
		}
	}
	
	///////////////////////////////////////////////// ADDERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Create a new empty Deal for the current User
	 * @param title
	 */
	public Contrat newDeal(String title){
		User currentUser = manager.getUserManager().getCurrentUser();
		if(currentUser == null){
			Printer.printError(this, "newDeal", "No user logged");
			return null;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		Contrat deal = new Contrat(title, currentUser);
		deals.get(publicKey).add(deal);
		return deal;
	}
	
	/**
	 * Add Deal to the user's publicKey. If deal is empty, it will abort.
	 * If the publicKey isn't an user's publicKey known, it will abort.
	 * @param publicKey
	 * @param deal
	 */
	public void addDeal(String publicKey, Contrat deal){
		if(deal == null){
			Printer.printError(this, "addDeal", "deal is empty");
			return;
		}
		if(manager.getUserManager().getUser(publicKey) == null){
			Printer.printError(this, "addDeal", "user is unknow");
			return;
		}
		if(!deals.containsKey(publicKey))
			deals.put(publicKey, new ArrayList<Contrat>());
		deals.get(publicKey).add(deal);
	}
	
	public boolean addItem(String contratID, Item item){
		Contrat contrat = null;
		
		User currentUser = manager.getUserManager().getCurrentUser();
		if(currentUser == null) {
			System.err.println("no user logged");
			return false;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		
		for(Contrat c : deals.get(publicKey)){
			if(c.getId().equals(contratID)){
				contrat = c;
				break;
			}
		}
		if(contrat == null){
			Printer.printError(this, "addItem", "Contrat doesn't exist !");
			return false;
		}
		return contrat.addItem(item);
	}
	
	public Collection<Contrat> getContrats() {
		ArrayList<Contrat> contrats = new ArrayList<Contrat>();
		for(ArrayList<Contrat> cs : deals.values()) {
			contrats.addAll(cs);
		}
		return contrats;
	}
	
	public void addContrat(Contrat c) {
		ArrayList<Contrat> contrats =  deals.get(c.getKeys().getPublicKey().toString(16));
		if(contrats == null) {
			contrats = new ArrayList<Contrat>();
			deals.put(c.getKeys().getPublicKey().toString(16), contrats);
		}
		if(contrats.contains(c)) {
			Contrat local = contrats.get(contrats.indexOf(c));
			if(local.getLastUpdated() < c.getLastUpdated()) {
				contrats.remove(local);
				contrats.add(c);
				c.publish(manager.getNetwork());
			}
		}
		else {
			contrats.add(c);
			c.publish(manager.getNetwork());
		}
	}
	//////////////////////////////////////////////// REMOVERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public boolean removeContrat(String publicKey, String contratID) {
		for(Contrat d : deals.get(publicKey)){
			if(d.getId().equals(contratID))
				return deals.get(publicKey).remove(d);
		}
		return false;
	}
	public boolean removeItemContrat(String itemKey, String contratID) {
		String publicKey = manager.getUserManager().getCurrentUser().getKeys().getPublicKey().toString(16);
		if(publicKey == null || publicKey.isEmpty())
			return false;
		for(Contrat d : deals.get(publicKey)){
			if(d.getId().equals(contratID)){
				return d.removeItem(itemKey);
			}
		}
		return false;
	}
	public boolean removeSignatoryContrat(String contratID, String publicKey) {
		String currentPublicKey = manager.getUserManager().getCurrentUser().getKeys().getPublicKey().toString(16);
		if(publicKey == null || publicKey.isEmpty())
			return false;
		if(currentPublicKey.equals(publicKey))
			return false;
		for(Contrat d : deals.get(publicKey)){
			if(d.getId().equals(contratID)){
				return d.removeSignatory(publicKey);
			}
		}
		return false;
	}
	
}
