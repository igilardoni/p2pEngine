package controller.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class AddItem extends AbstractInterlocutor {

	public AddItem() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		JSONObject c = getJSON(msg);
		String nick = c.getString("username");
		String oldPassword = c.getString("oldpassword");
		String newPassword = c.getString("password");
		String name = c.getString("name");
		String firstName = c.getString("firstname");
		String email = c.getString("email");
		String phone = c.getString("phone");
		boolean ok = ManagerBridge.updateAccount(nick, oldPassword, newPassword, name, firstName, email, phone);
		if(!ok){
			// Send error message
		}else{
			JSONObject data = new JSONObject();
			data.put("query", "accountUpdated");
			JSONObject content = new JSONObject();
			content.put("ok", "ok");
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
