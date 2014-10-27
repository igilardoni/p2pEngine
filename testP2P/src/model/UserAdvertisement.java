package model;

import net.jxta.document.AdvertisementFactory;
import net.jxta.document.Element;
import net.jxta.id.ID;

public class UserAdvertisement extends AbstractAdvertisement<User>{
	
	public UserAdvertisement(Element root) {
		super(root);
	}
	
	public UserAdvertisement(User user) {
		super();
		if(user == null) return;
		this.putValue("login", user.getLogin());
		this.putValue("nom", user.getNom());
		this.putValue("prenom", user.getPrenom());
		this.putValue("adresse", user.getAdresse());
		this.putValue("tel", user.getTel());
		this.putValue("password", Integer.toBinaryString(user.getPasswordHash()));
	}
	
	@Override
	protected void setKeys() {
		this.addKey("login", true);
		this.addKey("nom", false);
		this.addKey("prenom", false);
		this.addKey("adresse", false);
		this.addKey("tel", false);
		this.addKey("password", false);
		this.addKey("mail", false);
	}
	
	public static String getAdvertisementType() {
		return "jxta:" + UserAdvertisement.class.getName();
	}
	
	public static void register() {
		UserAdvertisement adv  = new UserAdvertisement((User)null);
		AdvertisementFactory.registerAdvertisementInstance(UserAdvertisement.getAdvertisementType(),
                										   new AdvertisementInstaciator(adv.getClass(), UserAdvertisement.getAdvertisementType()));
	}

	@Override
	public String getAdvType() {
		return UserAdvertisement.getAdvertisementType();
	}

	@Override
	public ID getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User toClass() {
		User user = new User(this.getValue("nom"), this.getValue("prenom"), this.getValue("adresse"), 
				this.getValue("tel"), this.getValue("mail"), this.getValue("login"), this.getValue("password"));
		user.setPassword(Integer.parseInt(this.getValue("password"), 2));
		return user;
	}

}
