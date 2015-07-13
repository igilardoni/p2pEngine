package view.interlocutors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class SignOut extends AbstractInterlocutor {

	public SignOut() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			String username = ManagerBridge.getCurrentUser().getNick();
			ManagerBridge.logout();
			JSONObject data = new JSONObject();
			data.put("query", "logout");
			JSONObject content = new JSONObject();
			content.put("username", username);
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
