package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadItemSearchField extends AbstractInterlocutor {

	public LoadItemSearchField() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		ArrayList<String> fields = ManagerBridge.getItemSearchableFields();
		for (String field : fields) {
			JSONObject data = new JSONObject();
			data.put("query", "itemSearchFieldLoaded");
			JSONObject content = new JSONObject();
			content.put("field", field);
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
