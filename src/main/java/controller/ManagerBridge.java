package controller;

import java.util.ArrayList;

import util.DateConverter;
import controller.controllerInterface.ManagerBridgeInterface;
import model.Application;
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.manager.Manager;
import model.data.user.User;

public class ManagerBridge implements ManagerBridgeInterface{
	private User current;
	
	public ManagerBridge(){
	}
	
	@Override
	public void registration(String nick,String password, String name, String firstName, String email, String phone){
		User user = new User(nick, password, name, firstName, email, phone);
		Application.getInstance().getManager().registration(user);
	}
	
	@Override
	public boolean login(String login, String password){
		if(Application.getInstance().getManager().getCurrentUser()!=null)
			Application.getInstance().getManager().logout();
		boolean logged = Application.getInstance().getManager().login(login, password);
		if(logged)
			current = Application.getInstance().getManager().getCurrentUser();
		return logged;
	}

	// a verifier ya un probleme avec la modification des informations utilisateur!!!
	@Override
	public boolean updateAccount(String nick, String oldPassword, String newPassword,
			String name, String firstName, String email, String phone){
		
	
		if(Application.getInstance().getManager().getCurrentUser() == null){
			System.err.println(this.getClass().getName()+".addItem : No user logged !");
			return false;
		}
		if(current.isPassword(oldPassword)){
			current.setNick(nick);
			current.setName(name);
			current.setFirstName(firstName);
			current.setEmail(email);
			current.setPassWord(newPassword);
			current.setClearPassword(newPassword);
			current.setPhone(phone);
			Application.getInstance().getManager().registration(current);
			Application.getInstance().getManager().logout();
			return Application.getInstance().getManager().login(nick, newPassword);
		}
		return false;
	}
	
	@Override
	public void addItem(String title, String category, String description, String image, String country, String contact, String lifeTime, String type ){
		
	
		if(notLogged()){
			System.err.println(this.getClass().getName()+".addItem : No user logged !");
			return;
		}
		Long l = DateConverter.getLongBefore(lifeTime);
		l = l>0?l:0L;
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
			t = TYPE.PROPOSAL;
		}
		System.out.println(Application.getInstance().getManager().getCurrentUser().getKeys().toString());
		Item item = new Item(Application.getInstance().getManager().getCurrentUser(), title, c, description, image, country, contact, 0, l, t);
		item.sign(Application.getInstance().getManager().getCurrentUser().getKeys());
		Application.getInstance().getManager().addItem(item, true);
	}

	@Override
	public void removeItem(String title) {
		if(notLogged()){
			System.err.println(this.getClass().getName()+".removeItem : No user logged !");
			return;
		}
		Item item  = Application.getInstance().getManager().getItem(Application.getInstance().getManager().getCurrentUser().getKeys().getPublicKey().toString(16), title);
		if(item != null)
			Application.getInstance().getManager().removeItem(item);
	}

	@Override
	public void updateItem(String title, String category, String description,
			String image, String country, String contact, String lifeTime,
			String type) {
		if(notLogged()){
			System.err.println(this.getClass().getName()+".updateItem : No user logged !");
			return;
		}
		addItem(title, category, description, image, country, contact, lifeTime, type);
	}
	
	private boolean notLogged(){
		return Application.getInstance().getManager().getCurrentUser() == null;
	}

	@Override
	public User getCurrentUser() {
		return Application.getInstance().getManager().getCurrentUser();
	}

	@Override
	public ArrayList<Item> getUserItems(String publicKey) {
		if(publicKey == null){
			System.err.println("public key empty");
			return null;
		}
		return Application.getInstance().getManager().getUserItems(publicKey);
	}

	@Override
	public ArrayList<Item> getCurrentUserItem() {
		return getUserItems(getCurrentUser().getKeys().getPublicKey().toString(16));
	}
}
