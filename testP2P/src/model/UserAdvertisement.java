package model;

import java.io.Serializable;

import view.Application;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.Element;
import net.jxta.id.ID;

public class UserAdvertisement extends AbstractAdvertisement<User> implements Serializable{
	
	private static final long serialVersionUID = -2830006496117978252L;

	public UserAdvertisement(Element root) {
		super(root);
	}
	
	public UserAdvertisement(User user) {
		super();
		if(user == null) return;
		this.putValue("login", user.getLogin());
		this.putValue("miniLogin", user.getLogin().toLowerCase());
		this.putValue("nom", user.getNom());
		this.putValue("prenom", user.getPrenom());
		this.putValue("adresse", user.getAdresse());
		this.putValue("tel", user.getTel());
		this.putValue("password", Integer.toString(user.getPasswordHash()));
		this.putValue("pid", Application.getInstance().getPeer().getPeerId().toURI().toString());
		this.putValue("mail", user.getMail());
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
		this.addKey("pid", false);
		this.addKey("miniLogin", true);
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
				this.getValue("tel"), this.getValue("mail"), this.getValue("login"), null);
		user.setPassword(Integer.parseInt(this.getValue("password")));
		user.setPeerID(this.getValue("pid"));
		return user;
	}

}
