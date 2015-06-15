package controller;

import java.util.ArrayList;

import model.Application;
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Item.TYPE;
import model.data.user.User;
import util.DateConverter;
import controller.controllerInterface.ManagerBridgeInterface;

public class ManagerBridge implements ManagerBridgeInterface{
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
		return Application.getInstance().getManager().login(login, password);
	}
	
	@Override
	public void logout(){
		Application.getInstance().getManager().logout();
	}
	
	@Override
	public boolean updateAccount(String nick, String oldPassword, String newPassword,
			String name, String firstName, String email, String phone){
		
	
		if(Application.getInstance().getManager().getCurrentUser() == null){
			System.err.println(this.getClass().getName()+".addItem : No user logged !");
			return false;
		}
		if(Application.getInstance().getManager().getCurrentUser().isPassword(oldPassword)){
			Application.getInstance().getManager().getCurrentUser().setNick(nick);
			Application.getInstance().getManager().getCurrentUser().setName(name);
			Application.getInstance().getManager().getCurrentUser().setFirstName(firstName);
			Application.getInstance().getManager().getCurrentUser().setEmail(email);
			Application.getInstance().getManager().getCurrentUser().setPassWord(newPassword);
			Application.getInstance().getManager().getCurrentUser().setClearPassword(newPassword);
			Application.getInstance().getManager().getCurrentUser().setPhone(phone);
			Application.getInstance().getManager().getCurrentUser().sign(Application.getInstance().getManager().getCurrentUser().getKeys());
			Application.getInstance().getManager().logout();
			return Application.getInstance().getManager().login(nick, newPassword);
		}
		return false;
	}
	
	@Override
	public String addItem(String title, String category, String description, String image, String country, String contact, String lifeTime, String type ){
		if(notLogged()){
			System.err.println(this.getClass().getName()+".addItem : No user logged !");
			return null;
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
		return item.getItemKey();
	}

	@Override
	public void removeItem(String itemKey) {
		if(notLogged()){
			System.err.println(this.getClass().getName()+".removeItem : No user logged !");
			return;
		}
		Item item  = Application.getInstance().getManager().getItem(itemKey);
		if(item != null)
			Application.getInstance().getManager().removeItem(item);
	}

	@Override
	public void updateItem(String itemKey, String title, String category,
			String description, String image, String country,
			String contact, String lifeTime, String type) {
		if(notLogged()){
			System.err.println(this.getClass().getName()+".updateItem : No user logged !");
			return;
		}
		Item item = new Item(Application.getInstance().getManager().getItem(itemKey).toString());
		item.setTitle(title);
		item.setCategory(new Category(category));
		item.setDescription(description);
		item.setImage(image);
		item.setCountry(country);
		item.setContact(contact);
		item.setLifeTime(Long.parseLong(lifeTime));
		item.setType(type.toUpperCase()=="WISH"?TYPE.WISH:TYPE.PROPOSAL);
		item.sign(Application.getInstance().getManager().getCurrentUser().getKeys());
		Application.getInstance().getManager().updateItem(itemKey, item);
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
	public ArrayList<Item> getCurrentUserItems() {
		return getUserItems(getCurrentUser().getKeys().getPublicKey().toString(16));
	}
	
	@Override
	public Item getCurrentUserItem(String itemKey){
		return Application.getInstance().getManager().getItem(itemKey);
	}

	@Override
	public void addFavoriteItem(Item item) {
		Application.getInstance().getManager().getFavoritesCurrentUser().addItem(item);
	}

	@Override
	public void removeFavoriteItem(String itemKey) {
		Application.getInstance().getManager().getFavoritesCurrentUser().removeItem(itemKey);
	}

	@Override
	public ArrayList<String> getFavoriteItemsKey() {
		return Application.getInstance().getManager().getFavoritesCurrentUser().getItemsKey();
	}
}
