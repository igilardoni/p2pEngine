package model.advertisement;

import model.item.Item;
import model.user.User;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory.Instantiator;
import net.jxta.document.Element;

/**
 * This class is a wrapper to construct an Instantiator for our Advertisement
 * Once the AdvertisementInstanciator registered to JXTA, JXTA can construct a received
 * advertisement automatically
 * @author Prudhomme Julien
 *
 */
public class AdvertisementInstaciator implements Instantiator{
	
	private Class<? extends Advertisement> advClass;
	private String advType;
	
	public AdvertisementInstaciator(Class<? extends Advertisement> advClass, String advType) {
		this.advClass = advClass;
		this.advType = advType;
	}
	
	@Override
	public String getAdvertisementType() {
		return advType;
	}

	@Override
	public Advertisement newInstance() {
		try {
			return advClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Advertisement newInstance(Element root) {
		try {
			advClass.getConstructor(Element.class).newInstance(root);
			return advClass.getConstructor(Element.class).newInstance(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void RegisterAllAdv() {
		Item.register();
		User.register();
	}

}
