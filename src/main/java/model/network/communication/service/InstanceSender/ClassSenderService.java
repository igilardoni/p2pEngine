package model.network.communication.service.InstanceSender;

import org.jdom2.Element;

import util.StringToElement;
import model.advertisement.AbstractAdvertisement;
import model.network.communication.Communication;
import model.network.communication.service.Service;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

public abstract class ClassSenderService<T extends AbstractAdvertisement> extends Service<AbstractAdvertisement> {
	
	public abstract T newInstance(Element root);
	
	@Override
	public AbstractAdvertisement handleMessage(Message m) {
		if(m.getMessageElement("content") == null) return null;
		Element xml = StringToElement.getElementFromString(new String(m.getMessageElement("content").getBytes(true)), "adv");
		return newInstance(xml);
	}

	@Override
	public void sendMessage(AbstractAdvertisement data, PeerID... ids) {
		sender.sendMessage(data.toString(), this.getServiceName(), ids);
	}

	
	public static void addSenderServices(Communication com) {
		com.addService(new ContratSender());
		com.addService(new FavoritesSender());
		com.addService(new ItemSender());
		com.addService(new UserSender());
	}
	

}
