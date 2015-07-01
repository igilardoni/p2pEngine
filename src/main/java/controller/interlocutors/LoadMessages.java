package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.data.user.UserMessage;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.secure.AsymKeysImpl;
import controller.ManagerBridge;

public class LoadMessages extends AbstractInterlocutor {

	public LoadMessages() {
	}
	
	public static String content;
	
	@Override
	public void init(String content, Session session){
		this.content = content;
		AbstractInterlocutor.com = session.getAsyncRemote();
	}
	
	@Override
	public void reset(){
		this.content = null;
	}
	
	@Override
	public boolean isInitialized(){
		return this.content != null && this.com != null;
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			ArrayList<UserMessage> messages = ManagerBridge.getMessages();
			AsymKeysImpl key = ManagerBridge.getCurrentUser().getKeys();
			for (UserMessage message : messages) {
				JSONObject data = new JSONObject();
				data.put("query", "messagesLoaded");
				JSONObject content = new JSONObject();
				content.put("date", message.getDate());
				content.put("id", message.getID());
				content.put("from", message.getSender().getPublicKey());
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
