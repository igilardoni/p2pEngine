package controller.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import model.data.user.Message;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadMessage extends AbstractInterlocutor {

	public LoadMessage() {
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
			JSONObject c = getJSON(content);
			String id = c.getString("id");
			Message message = ManagerBridge.getMessage(id);
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			if(message == null){
				data.put("query", "messageUnLoaded");
				content.put("error", "unknow message");
			}else{
				data.put("query", "messageLoaded");
				content.put("id", message.getID());
				content.put("message", message.getMsg(ManagerBridge.getCurrentUser().getKeys()));
				content.put("date", message.getDate());
				content.put("from", message.getSender(ManagerBridge.getCurrentUser().getKeys()));
			}
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
