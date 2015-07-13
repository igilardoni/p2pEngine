package view.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class UpdateAccount extends AbstractInterlocutor {

	public UpdateAccount() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
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
				com.sendText(data.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
