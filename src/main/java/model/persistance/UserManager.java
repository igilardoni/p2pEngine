package model.persistance;

import model.entity.User;

public class UserManager extends AbstractEntityManager<User>{
	public UserManager() {
		super();
		this.initialisation("users", User.class);
	}
}
