package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.data.user.Message;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.secure.AsymKeysImpl;
import controller.ManagerBridge;

public class LoadMessages extends AbstractInterlocutor {

	public LoadMessages() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		ArrayList<Message> messages = ManagerBridge.getMessages();
		AsymKeysImpl key = ManagerBridge.getCurrentUser().getKeys();
		for (Message message : messages) {
			JSONObject data = new JSONObject();
			data.put("query", "messagesLoaded");
			JSONObject content = new JSONObject();
			content.put("date", message.getDate());
			content.put("id", message.getID());
			content.put("from", message.getSender(key));
			content.put("subject", message.getMsg(key).substring(0, 10));
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
