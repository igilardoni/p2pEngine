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

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		JSONObject c = getJSON(msg);
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
		session.getBasicRemote().sendText(data.toString());
	}

}
