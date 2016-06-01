package network.impl.jxta;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;

import model.entity.Item;
import model.persistance.ItemManager;
import net.jxta.document.AdvertisementFactory;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.pipe.OutputPipe;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import network.api.Peer;
import rest.util.JsonUtils;

public class JxtaItemsSenderService extends JxtaService implements PipeMsgListener{
	public static final String NAME = "itemsSender";
	private String response = "empty !";
	
	public JxtaItemsSenderService() {
		this.name = NAME;
	}
	
	@Override
	public void initAndStart(Peer peer) {
		super.initAndStart(peer);
		createInputPipe();
	}
	
	private void createInputPipe() {
		try {
			pg.getPipeService().createInputPipe(getAdvertisement(), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getResponse() {
		return response;
	}
	
	/**
	 * Create a simple advertisement for the pipes' class.
	 * @return
	 */
	protected PipeAdvertisement getAdvertisement() {
		return getPipeAdvertisement(IDFactory
				.newPipeID(pg.getPeerGroupID(), this.getClass().getName().getBytes()), false);
	}
	
	protected PipeAdvertisement getPipeAdvertisement(PipeID id, boolean is_multicast) {
        PipeAdvertisement adv = (PipeAdvertisement )AdvertisementFactory.
            newAdvertisement(PipeAdvertisement.getAdvertisementType());
        adv.setPipeID(id);
        if (is_multicast)
            adv.setType(PipeService.PropagateType); 
        else 
            adv.setType(PipeService.UnicastType); 
        adv.setName("Pipe");
        adv.setDescription("...");
        return adv;
    }

	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		Message m = event.getMessage();
		String type = new String(m.getMessageElement("type").getBytes(true));
		switch(type) {
		case "request": processRequest(m); break;
		case "response": processResponse(m); break;
		}
	}
	
	private void processRequest(Message m) {
		String title = new String(m.getMessageElement("title").getBytes(true));
		String uri = new String(m.getMessageElement("peerid").getBytes(true));
		 
		sendMessage(getResponseMessage(title), uri);
	}
	
	private void processResponse(Message m) {
		response = new String(m.getMessageElement("items").getBytes(true));
	}
	
	private Message getRequestMessage(String title) {
		Message m = new Message();
		m.addMessageElement(new ByteArrayMessageElement("type", null, new String("request").getBytes(), null));
		m.addMessageElement(new ByteArrayMessageElement("title", null, title.getBytes(), null));
		m.addMessageElement(new ByteArrayMessageElement("peerid", null, peerUri.getBytes(), null));
		return m;
	}
	
	private Message getResponseMessage(String title) {
		Message m = new Message();
		m.addMessageElement(new ByteArrayMessageElement("type", null, new String("response").getBytes(), null));
		ItemManager im = new ItemManager();
		Collection<Item> items = im.findManyByAttribute("title", title);
		m.addMessageElement(new ByteArrayMessageElement("items", null, JsonUtils.collectionStringify(items).getBytes(), null));
		return m;
	}
	
	public void sendRequest(String title, String ...peerURIs) {
		sendMessage(getRequestMessage(title), peerURIs);
	}
	
	public void sendMessage(Message m, String ...peerURIs) {
		HashSet<PeerID> to = new HashSet<PeerID>();
		for(String pidUri: peerURIs) {
			try {
				PeerID pid = PeerID.create(new URI(pidUri));
				to.add(pid);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		try {
			OutputPipe pipe = pg.getPipeService().createOutputPipe(getAdvertisement(), to, 3000);
			pipe.send(m);
			pipe.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
