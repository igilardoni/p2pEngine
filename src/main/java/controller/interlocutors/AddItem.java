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
		String category = c.getString("category");
		String contact = c.getString("contact");
		String country = c.getString("country");
		String description = c.getString("description");
		String image = c.getString("image");
		String lifeTime = c.getString("lifetime");
		String title = c.getString("title");
		String type = c.getString("type");
		String itemKey = ManagerBridge.addItem(title, category, description, image, country, contact, lifeTime, type);
		// Answer
		if(itemKey == null || itemKey.isEmpty()){
			// Send error message
		}else{
			JSONObject data = new JSONObject();
			data.put("query", "itemAdded");
			JSONObject content = new JSONObject();
			content.put("itemKey", itemKey);
			content.put("title", title);
			content.put("description", description);
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
