package view.interlocutors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class SignIn extends AbstractInterlocutor {
	public SignIn() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			if(ManagerBridge.login((String) c.getString("username"), (String) c.getString("password"))){
				JSONObject data = new JSONObject();
				data.put("query", "login");
				JSONObject content = new JSONObject();
				content.put("ok", "ok");
				content.put("message", "user logged");
				data.put("content", content);
				com.sendText(data.toString());
			}else{
				JSONObject data = new JSONObject();
				data.put("query", "login");
				JSONObject content = new JSONObject();
				content.put("ok", "no");
				content.put("message", "unknown account");
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
