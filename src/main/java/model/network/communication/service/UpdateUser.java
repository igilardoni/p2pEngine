package model.network.communication.service;

import org.jdom2.Element;

import util.StringToElement;
import model.item.Category;
import model.item.Item;
import model.item.Item.TYPE;
import model.manager.Manager;
import model.network.Network;
import model.network.communication.Communication;
import model.network.search.Search;
import model.network.search.Search.Result;
import model.user.User;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

/**
 * This class handle message for updating / creating new users with user's items
 * @author Julien
 *
 */
public class UpdateUser extends Service<String>{

	private Manager manager;
	public UpdateUser(Manager m) {
		super();
		manager = m;
	}
	
	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Check if the received Element is correctly formed.
	 * @param root
	 * @return
	 */
	private boolean checkRootFormat(Element root) {
		return root.getChild("User") != null &&
				root.getChild("Items") != null;
	}
	
	private boolean addUserIfMoreRecent(User u) {
		User u2 = manager.whoIs(u.getKeys().getPublicKey());
		if(u2 != null) {
			if(u2.getLastUpdated() > u.getLastUpdated()) return false;
			manager.removeUserWithItems(u2);
		}
		manager.addUser(u);
		return true;
		
	}
	
	public boolean receiveUser(Element root) {
		User u = new User(root.getChild("User"));
		if(!u.checkSignature(u.getKeys())) return false;
		if(!addUserIfMoreRecent(u)) return false; //our user is more recent, doesn't need to update item or user.
		for(Element e: root.getChild("Items").getChildren()) {
			Item i = new Item(e);
			if(!i.checkSignature(u.getKeys())) continue;
			manager.addItem(i);
		}
		return true;
	}
	
	public boolean receiveRequest(Element root) {
		String pkey = root.getChild("RequestUser").getValue();
		//sendMessage(data, ids);
		return true;
	}
	
	@Override
	public String handleMessage(Message m) {
		String content = new String(m.getMessageElement("content").getBytes(true));
		Element root = StringToElement.getElementFromString(content, "UserData");
		if(!checkRootFormat(root)) return null; //message format incorrect
		
		return content;
	}

	@Override
	public void sendMessage(String data, PeerID ... ids) {
		sender.sendMessage(data, getServiceName(), ids);
	}
	
	
	public void requestUser(String publicKey) {
		Search<User> s = new Search<User>(getNetwork().getGroup("users").getDiscoveryService(), "publicKey", true);
		s.search(publicKey, 5000, 5);
		PeerID[] pids = new PeerID[s.getResultsWithPeerID().size()];
		int i = 0;
		for(Result r: s.getResultsWithPeerID()) {
			pids[i++] = r.peerID;
		}
		sender.sendMessage("<RequestUser>" + publicKey + "</RequestUser>", getServiceName(), pids);
		
	}
	
	// TEST
	
	public static void main(String[] args) {
		Network n = new Network(9700, ".test", "test");
		n.start();
		Communication c = null;
		try {
			c = new Communication(n);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Manager m = new Manager(n);
		UpdateUser uu = new UpdateUser(m);
		c.addService(uu);
		User u = new User("nickname", "password", "nom", "prenom", "mail@mail.mail", "0650507224");
		u.sign(u.getKeys());
		Item i = new Item(u, "patate", new Category(Category.CATEGORY.Appliances), "description", "une image", "france", "contact", 0, 0, TYPE.WISH);
		i.sign(u.getKeys());
		m.addUser(u);
		m.addItem(i);
		
		String xml = m.UserItemXMLString(u.getKeys().getPublicKey().toString(16));
		Message message = new Message();
		message.addMessageElement(new ByteArrayMessageElement("content", null, xml.getBytes(), null));
		uu.handleMessage(message);
	}

}
