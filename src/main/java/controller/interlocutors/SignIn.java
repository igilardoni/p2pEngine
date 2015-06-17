package controller.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class SignIn extends AbstractInterlocutor {
	public SignIn() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException, IOException {
		System.out.println("test");
		JSONObject c = getJSON(msg);
		if(ManagerBridge.login((String) c.getString("username"), (String) c.getString("password"))){
			JSONObject data = new JSONObject();
			data.put("query", "login");
			JSONObject content = new JSONObject();
			content.put("ok", "ok");
			content.put("message", "user logged");
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}else{
			JSONObject data = new JSONObject();
			data.put("query", "login");
			JSONObject content = new JSONObject();
			content.put("ok", "no");
			content.put("message", "unknown account");
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
