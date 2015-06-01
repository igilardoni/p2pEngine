package controller;

import controller.controllerInterface.ManagerBridgeInterface;
import model.Application;
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.manager.Manager;
import model.data.user.User;

public class ManagerBridge implements ManagerBridgeInterface{
	private Manager manager;
	private User current;
	
	public ManagerBridge(){
		manager = Application.getInstance().getManager();
	}
	
	@Override
	/**
	 * Add NEW User to current Manager
	 * @param nick
	 * @param password
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 */
	public void registration(String nick,String password, String name, String firstName, String email, String phone){
		User user = new User(nick, password, name, firstName, email, phone);
		manager.registration(user);
	}
	
	@Override
	/**
	 * Return true if, only if, login exists and password is good
	 * @param login
	 * @param password
	 * @return
	 */
	public boolean login(String login, String password){
		boolean logged = manager.login(login, password);
		if(logged)
			current = manager.getCurrentUser();
		return logged;
	}
	
	@Override
	/**
	 * add an current user's item in the manager 
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param country
	 * @param contact
	 * @param lifeTime
	 * @param type
	 */
	public void addItem(String title, String category, String description, String image, String country, String contact, long lifeTime, String type ){
		if(logged()){
			System.err.println(this.getClass().getName()+".addItem : No user logged !");
			return;
		}
		User owner = Application.getInstance().getManager().getCurrentUser();
		Category c = new Category(category);
		Item.TYPE t;
		switch(type.toUpperCase()){
		case "WISH":
			t = TYPE.WISH;
			break;
		case "PROPOSAL":
			t = TYPE.PROPOSAL;
			break;
		default:
			t = TYPE.WISH;
		}
		Item item = new Item(owner, title, c, description, image, country, contact, 0, lifeTime, t);
		item.sign(owner.getKeys());
		manager.addItem(item);
	}

	@Override
	public void updateAccount(String nick, String password, String name,
			String firstName, String email, String phone) {
		if(current == null){
			System.err.println(this.getClass().getName()+".addItem : No user logged !");
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeItem(String title) {
		if(logged()){
			System.err.println(this.getClass().getName()+".removeItem : No user logged !");
			return;
		}
		Item item  = manager.getItem(current.getKeys().getPublicKey().toString(16), title);
		if(item != null)
			manager.removeItem(item);
	}

	@Override
	public void updateItem(String title, String category, String description,
			String image, String country, String contact, long lifeTime,
			String type) {
		if(logged()){
			System.err.println(this.getClass().getName()+".updateItem : No user logged !");
			return;
		}
		addItem(title, category, description, image, country, contact, lifeTime, type);
	}
	
	private boolean logged(){
		return manager.getCurrentUser() != null;
	}
}
