package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory.Instantiator;
import net.jxta.document.Element;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Advertisement newInstance(Element root) {
		try {
			advClass.getConstructor(Element.class).newInstance(root);
			return advClass.getConstructor(Element.class).newInstance(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void RegisterAllAdv() {
		ObjetAdvertisement.register();
		UserAdvertisement.register();
	}

}
