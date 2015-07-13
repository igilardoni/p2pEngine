package view.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadItemSearchField extends AbstractInterlocutor {

	public LoadItemSearchField() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try{
			ArrayList<String> fields = ManagerBridge.getItemSearchableFields();
			for (String field : fields) {
				JSONObject data = new JSONObject();
				data.put("query", "itemSearchFieldLoaded");
				JSONObject content = new JSONObject();
				content.put("field", field);
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
