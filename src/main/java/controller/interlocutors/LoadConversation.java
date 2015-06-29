package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.data.user.Message;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.secure.AsymKeysImpl;
import controller.ManagerBridge;

public class LoadConversation extends AbstractInterlocutor {

	public LoadConversation() {
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
				ArrayList<Message> messages = ManagerBridge.getConversation();
				AsymKeysImpl key = ManagerBridge.getCurrentUser().getKeys();
				for (Message message : messages) {
				JSONObject data = new JSONObject();
					data.put("query", "conversationLoaded");
					
					JSONObject content = new JSONObject();
					content.put("date", message.getDate());
					content.put("id", message.getID());
					content.put("from", message.getSender(key));
					content.put("subject", message.getMsg(key).substring(0, 10));
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
