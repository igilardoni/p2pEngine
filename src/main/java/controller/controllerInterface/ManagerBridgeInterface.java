package controller.controllerInterface;

public interface ManagerBridgeInterface {
	public void registration(String nick, String password, String name, String firstName, String email, String phone);
	public boolean login(String nick, String password);
	public void updateAccount(String nick, String password, String name, String firstName, String email, String phone);
	
	public void addItem(String title, String category, String description, String image, String country, String contact, long lifeTime, String type );
	public void removeItem(String title);
	public void updateItem(String title, String category, String description, String image, String country, String contact, long lifeTime, String type );
}
