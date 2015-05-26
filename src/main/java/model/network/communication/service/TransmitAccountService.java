package model.network.communication.service;

import model.manager.Manager;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

public class TransmitAccountService extends Service<Manager> {

	@Override
	public String getServiceName() {
		return this.getClass().getName();
	}

	
	
	
	@Override
	public Manager handleMessage(Message m) {
		Manager manager = new Manager(new String(m.getMessageElement("content").getBytes(true)), null);
		//TODO publish new data
		return manager;
	}

	@Override
	/**
	 * Transmit manager data to peers.
	 */
	public void sendMessage(Manager m, PeerID ...ids) {
		sender.sendMessage(m.toString(),this.getServiceName(), ids);
	}

}