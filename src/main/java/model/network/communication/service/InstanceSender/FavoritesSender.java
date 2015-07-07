package model.network.communication.service.InstanceSender;

import org.jdom2.Element;

import model.data.favorites.Favorites;

public class FavoritesSender extends ClassSenderService<Favorites>{

	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Favorites newInstance(Element root) {
		return new Favorites(root);
	}

}
