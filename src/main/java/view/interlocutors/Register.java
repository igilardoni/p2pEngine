package view.interlocutors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class Register extends AbstractInterlocutor {

	public Register() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String nick = c.getString("username");
			String password = c.getString("password");
			String name = c.getString("name");
			String firstName = c.getString("firstname");
			String email = c.getString("email");
			String phone = c.getString("phone");
			ManagerBridge.registration(nick, password, name, firstName, email, phone);
			
			JSONObject data = new JSONObject();
			data.put("query", "registration");
			c.put("ok", "ok");
			data.put("content", c);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}
}
