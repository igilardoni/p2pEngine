package model.network.communication.service.InstanceSender;

import model.data.item.Item;

import org.jdom2.Element;

public class ItemSender extends ClassSenderService<Item>{

	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Item newInstance(Element root) {
		return new Item(root);
	}

}
