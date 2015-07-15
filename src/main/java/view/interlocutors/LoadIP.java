package view.interlocutors;

import model.Application;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadIP extends AbstractInterlocutor {

	public LoadIP() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			data.put("query", "ipLoaded");
			content.put("ip", Application.getInstance().getNetwork().getBootStrapIp());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
