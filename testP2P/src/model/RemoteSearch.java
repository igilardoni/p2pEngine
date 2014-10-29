package model;

import java.util.*;

import view.Application;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;

public class RemoteSearch<T extends Advertisable> implements DiscoveryListener {
	
	private DiscoveryService discovery;
	private String attribute;
	private ArrayList<T> result = new ArrayList<T>();
	ArrayList<AbstractAdvertisement> result1 = new ArrayList<AbstractAdvertisement>();
	private long waitTime;
	
	public RemoteSearch(DiscoveryService discovery, String attribute, long waitTime) {
		this.discovery = discovery;
		this.attribute = attribute;
		this.waitTime = waitTime;
	}
	
	
	public ArrayList <T> getRemoteSearch(final String value) {
		
		final RemoteSearch<T> thisInstance = this;
		discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, attribute,value,10, thisInstance);
		long waiting = waitTime;
		
		while(waiting > 0) {
			long currentTime = System.currentTimeMillis();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			waiting -= System.currentTimeMillis()-currentTime;
		}
		for (AbstractAdvertisement<T> i : result1)
		{
			result.add(i.toClass());
		}
		ArrayList <T> res = (ArrayList<T>) result;
	    result = null;
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		result1 =  (ArrayList<AbstractAdvertisement>) Collections.list((Enumeration<T>)event.getResponse().getAdvertisements());
	}

}
