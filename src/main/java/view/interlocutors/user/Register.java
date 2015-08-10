package view.interlocutors.user;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class Register extends AbstractInterlocutor {

	public Register() {
		super();
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
			c.put("feedbackOk", true);
			c.put("feedback", "Account created locally. You must log in at least once to complete registration.");
			data.put("content", c);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}
}
