package controller.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class UpdateItem extends AbstractInterlocutor {

	public UpdateItem() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		JSONObject c = getJSON(msg);
		String itemKey = c.getString("itemKey");
		String category = c.getString("category");
		String contact = c.getString("contact");
		String country = c.getString("country");
		String description = c.getString("description");
		String image = c.getString("image");
		String lifeTime = c.getString("lifetime");
		String title = c.getString("title");
		String type = c.getString("type");
		ManagerBridge.updateItem(itemKey, title, category, description, image, country, contact, lifeTime, type);

		JSONObject data = new JSONObject();
		data.put("query", "itemUpdated");
		JSONObject content = new JSONObject();
		content.put("itemKey", itemKey);
		content.put("title", title);
		content.put("description", description);
		data.put("content", content);
		session.getBasicRemote().sendText(data.toString());
	}

}
