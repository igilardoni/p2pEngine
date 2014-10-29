package model;

import java.io.IOException;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;

public abstract class AbstractAdvertisable implements Advertisable{

	private Advertisement lastAdv = null;
	
	@Override
	public abstract Advertisement getAdvertisement();

	@Override
	public void publish(DiscoveryService discovery) {
		lastAdv = getAdvertisement();
		try {
			discovery.publish(lastAdv);
			discovery.remotePublish(lastAdv);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void flush(DiscoveryService discovery) {
		try {
			discovery.flushAdvertisement(lastAdv);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(DiscoveryService discovery) {
		flush(discovery);
		publish(discovery);
	}

}
