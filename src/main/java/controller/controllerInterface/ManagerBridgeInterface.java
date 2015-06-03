package controller.controllerInterface;

import java.util.ArrayList;

public interface ManagerBridgeInterface {
	/**
	 * Add NEW User to current Manager
	 * @param nick
	 * @param password
	 * @param name
	 * @param firstName
	 * @param login
	 * @param login
	 */
	public void registration(String nick, String password, String name, String firstName, String email, String phone);
	/**
	 * Return true if, only if, login exists and password is good
	 * @param login
	 * @param password
	 * @return
	 */
	public boolean login(String nick, String password);
	/**
	 * Update the current account
	 * @param nick
	 * @param oldPassword
	 * @param newPassword
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 * @return
	 */
	public boolean updateAccount(String nick, String oldPassword, String newPassword, String name, String firstName, String email, String phone);
	

	/**
	 * Add an current user's item in the manager 
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param country
	 * @param contact
	 * @param lifeTime
	 * @param type
	 */
	public void addItem(String title, String category, String description, String image, String country, String contact, String lifeTime, String type );
	/**
	 * Remove item with title for the current User
	 * @param title
	 */
	public void removeItem(String title);
	/**
	 * Update item with title for the current user
	 * Care, the title can't be changed !
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param country
	 * @param contact
	 * @param lifeTime
	 * @param type
	 */
	public void updateItem(String title, String category, String description, String image, String country, String contact, String lifeTime, String type );

	/**
	 * load all informations of current user (return String)
	 */
	//public String load_use();

	/**
	 * load all items of current user
	 */
	//public ArrayList<String> load_items();
}
