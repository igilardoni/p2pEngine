package controller.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class SignOut extends AbstractInterlocutor {

	public SignOut() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		String username = ManagerBridge.getCurrentUser().getNick();
		ManagerBridge.logout();
		JSONObject data = new JSONObject();
		data.put("query", "logout");
		JSONObject content = new JSONObject();
		content.put("username", username);
		data.put("content", content);
		session.getBasicRemote().sendText(data.toString());
	}

}
