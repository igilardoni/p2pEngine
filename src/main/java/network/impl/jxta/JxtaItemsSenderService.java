package network.impl.jxta;

import java.util.Collection;
import model.entity.Item;
import model.persistance.ItemManager;
import net.jxta.pipe.PipeMsgEvent;
import network.api.ItemRequestService;
import network.api.Messages;
import network.impl.MessagesGeneric;
import network.impl.messages.RequestItemMessage;
import rest.util.JsonUtils;

public class JxtaItemsSenderService extends JxtaService implements ItemRequestService{
	public static final String NAME = "itemsSender";
	
	public JxtaItemsSenderService() {
		this.name = NAME;
	}
	
	private Messages getResponseMessage(String title) {
		MessagesGeneric m = new MessagesGeneric();
		
		m.addField("type", "response");
		ItemManager im = new ItemManager();
		Collection<Item> items = im.findManyByAttribute("title", title);
		m.addField("items", JsonUtils.collectionStringify(items));
		
		return m;
	}
	
	public void sendRequest(String title, String who, String ...peerURIs) {
		RequestItemMessage m = new RequestItemMessage();
		m.setTitle(title);
		m.setWho(who);
		m.setSource(this.peerUri);
		this.sendMessages(m, peerURIs);
	}
	
	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		Messages message = toMessages(event.getMessage());
		if(message.getMessage("type").equals("response")) {
			super.pipeMsgEvent(event);
			return;
		}
		
		this.sendMessages(getResponseMessage(message.getMessage("title")), message.getMessage("source"));
		
	}
}
