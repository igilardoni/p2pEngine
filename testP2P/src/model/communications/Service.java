package model.communications;

import java.util.ArrayList;

import net.jxta.endpoint.Message;

public abstract class Service implements MessageService{

	private ArrayList<MessageServiceListener> listeners = new ArrayList<MessageServiceListener>();
	
	@Override
	public abstract String getServiceName();
	
	public void addListener(MessageServiceListener l) {
		listeners.add(l);
	}
	
	public void notifyListeners(MessageData msg) {
		for(MessageServiceListener l: listeners) {
			l.messageEvent(msg);
		}
	}
	
	public void putMessage(Message message) {
		MessageData msg = handleMessage(message);
		if(msg != null) notifyListeners(msg);
	}
	
	protected abstract MessageData handleMessage(Message message);

}
