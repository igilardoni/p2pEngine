package model.network.communication.service.InstanceSender;

import org.jdom2.Element;

import model.data.user.User;

public class UserSender extends ClassSenderService<User>{

	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public User newInstance(Element root) {
		return new User(root);
	}

}
