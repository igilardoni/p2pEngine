package view.interlocutors.message;

import java.util.ArrayList;

import model.data.user.UserMessage;
import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadMessages extends AbstractInterlocutor {

	public LoadMessages() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			ArrayList<UserMessage> messages = new ArrayList<UserMessage>();
			messages.addAll(ManagerBridge.getCurrentUserMessages());
			//AsymKeysImpl key = ManagerBridge.getCurrentUser().getKeys();
			for (UserMessage message : messages) {
				JSONObject data = new JSONObject();
				data.put("query", "messagesLoaded");
				JSONObject content = new JSONObject();
				content.put("date", DateConverter.getString(message.getDate()));
				content.put("id", message.getId());
				if(message.getReceiver().getPublicKey().equals(ManagerBridge.getCurrentUser().getKeys().getPublicKey())) {
					content.put("isRead", message.isRead());
				}
				content.put("sender", message.getSender().getPublicKey().toString(16));
				content.put("receiver", message.getReceiver().getPublicKey().toString(16));
				content.put("subject", message.getSubject());
				data.put("content", content);
				com.sendText(data.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
