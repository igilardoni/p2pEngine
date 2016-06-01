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

	protected PeerGroup pg = null;
	private SearchListener<Advertisement<ElGamalSign>> currentSl;
	protected String name;
	protected String peerUri = null;
	
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
		@SuppressWarnings("unchecked")
		JxtaAdvertisement jxtaAdv = new JxtaAdvertisement((Advertisement<ElGamalSign>) adv);
		try {
			pg.getDiscoveryService().publish(jxtaAdv.getJxtaAdvertisementBridge());
			pg.getDiscoveryService().remotePublish(jxtaAdv.getJxtaAdvertisementBridge());
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
		peerUri = peer.getUri();
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
		pg.getDiscoveryService().getRemoteAdvertisements(null, DiscoveryService.ADV, attribute, value, 10, this);
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
			Advertisement<ElGamalSign> fadv = adv.getAdvertisement(); 
			fadv.setSourceURI("urn:jxta:" + event.getSource().toString().substring(7));
			
			advertisements.add(adv.getAdvertisement());
		}
		currentSl.notify(advertisements);
	}
}
