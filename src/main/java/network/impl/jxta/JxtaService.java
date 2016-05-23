package network.impl.jxta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.peergroup.PeerGroup;
import network.api.Advertisement;
import network.api.Peer;
import network.api.SearchListener;
import network.api.Service;
import protocol.impl.sigma.ElGamalSign;

/**
 * This is the Jxta implementation of {@link Service}
 * @see Peer
 * @author Julien Prudhomme
 *
 */
public class JxtaService implements Service, DiscoveryListener{

	private PeerGroup pg = null;
	private SearchListener<Advertisement<ElGamalSign>> currentSl;
	protected String name;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishAdvertisement(Advertisement<?> adv) {
		JxtaAdvertisement jxtaAdv = (JxtaAdvertisement) adv;
		try {
			pg.getDiscoveryService().publish(jxtaAdv.getJxtaAdvertisementBridge());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initAndStart(Peer peer) {
		if(!(peer instanceof JxtaPeer)) {
			throw new RuntimeException("Need a Jxta Peer to run a Jxta service");
		}
		JxtaPeer jxtaPeer = (JxtaPeer) peer;
		jxtaPeer.addService(this);
	}

	protected void setPeerGroup(PeerGroup pg) {
		this.pg = pg;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void search(String attribute, String value, SearchListener<?> sl) {
		this.currentSl = (SearchListener<Advertisement<ElGamalSign>>) sl;
		pg.getDiscoveryService().getRemoteAdvertisements(null, DiscoveryService.ADV, attribute, value, 0, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		Enumeration<net.jxta.document.Advertisement> advs = event.getResponse().getAdvertisements();
		ArrayList<Advertisement<ElGamalSign>> advertisements = new ArrayList<>();
		while(advs.hasMoreElements()) {
			AdvertisementBridge adv = (AdvertisementBridge) advs.nextElement();
			advertisements.add(adv.getAdvertisement());
		}
		currentSl.notify(advertisements);
	}
}
