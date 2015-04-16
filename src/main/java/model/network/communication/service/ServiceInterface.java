package model.network.communication.service;

import net.jxta.endpoint.Message;

public interface ServiceInterface {
	public String getServiceName();
	public void putMessage(Message m);
}
