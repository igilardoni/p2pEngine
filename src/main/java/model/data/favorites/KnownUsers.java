package model.data.favorites;

import java.util.HashMap;

import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;
import model.data.user.User;
import util.secure.AsymKeysImpl;

public class KnownUsers extends AbstractAdvertisement {

	public HashMap<String, AsymKeysImpl> users;
	
	public KnownUsers(User owner) {
		super();
		this.setKeys(owner.getKeys());
	}
	
	public KnownUsers(String xml) {
		super(xml);
	}
	
	public KnownUsers(Element root) {
		super(root);
	}
	
	public KnownUsers(net.jxta.document.Element root) {
		super(root);
	}
	
	@Override
	public String getSimpleName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected String getAdvertisementName() {
		return this.getClass().getName();
	}

	@Override
	protected void setKeys() {
		users = new HashMap<>();
		addKey("users", false, true);
	}

	@Override
	protected void putValues() {
		StringBuffer sb = new StringBuffer();
		for(String s: users.keySet()) {
			sb.append("<user>");
			sb.append("<nick>" + s + "</nick>");
			sb.append("<publicKey>" + users.get(s).toString() + "</publicKey>");
			sb.append("</user>");
		}
	}

	private boolean parseUsers(Element root) {
		for(Element e : root.getChildren()) {
			if(!e.getName().equals("user")) return false;
			String nick = e.getChild("nick").getValue();
			String publicKey = e.getChild("publicKey").getValue();
			users.put(nick, new AsymKeysImpl(publicKey));
		}
		return true;
	}
	
	@Override
	protected boolean handleElement(Element e) {
		if(e.getName().equals("users")) {
			return parseUsers(e);
		}
		else return false;
	}
	
	/**
	 * Add a known user to the user list.
	 * @param customName Name that the user choose
	 * @param u user to save
	 * @return false is the nickname was already taken
	 */
	public boolean add(String customName, User u) {
		if(users.containsKey(customName)) return false;
		users.put(customName, u.getKeys());
		return true;
	}
	
	/**
	 * Add a known user to the user list, with the custom name equals to user nickname
	 * @param u
	 * @return
	 */
	public boolean add(User u) {
		return add(u.getNick(), u);
	}
	
	public HashMap<String, AsymKeysImpl> getUsers() {
		return users;
	}
	
	public AsymKeysImpl get(String customName) {
		return users.get(customName);
	}

}
