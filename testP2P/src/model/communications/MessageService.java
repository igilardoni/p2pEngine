package model.communications;

import net.jxta.endpoint.Message;

public interface MessageService {
	public String getServiceName();
	public void putMessage(Message message);
}
