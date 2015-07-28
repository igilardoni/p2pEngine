package model.network.communication.service;

import java.util.ArrayList;

import model.Application;
import model.data.user.User;
import model.data.user.UserMessage;
import model.network.search.Search;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

public class MessageService extends Service<UserMessage> {

	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public UserMessage handleMessage(Message m) {
		if(m.getMessageElement("content") == null) return null;
		UserMessage msg = new UserMessage(new String(m.getMessageElement("content").getBytes(true)));
		User u = Application.getInstance().getManager().getUserManager().getCurrentUser();
		if(u != null) {
			if(u.getKeys().getPublicKey().equals(msg.getReceiver().getPublicKey())) {
				Application.getInstance().getManager().getMessageManager().getCurrentUserConversations().addMessage(msg);
				return msg;
			}
		}
		
		Application.getInstance().getManager().getMessageManager().addMessage(msg);
		return null;
		
	}

	public void sendMessage(UserMessage data) {
		sendMessage(data, (PeerID[])null);
	}
	
	@Override
	public void sendMessage(UserMessage data, PeerID... ids) {
		Search<User> s = new Search<User>(this.getNetwork(), User.class.getSimpleName(),
				"publicKey", true);
		s.search(data.getReceiver().getPublicKey().toString(16), 3, 5);
		ArrayList<Search<User>.Result> r = s.getResultsWithPeerID();
		PeerID[] pids = new PeerID[r.size()];
		for(int i = 0; i < r.size(); i++) {
			pids[i] = r.get(i).peerID;
			i++;
		}
		
		sender.sendMessage(data.toString(), this.getServiceName(), pids);
		
	}

}
