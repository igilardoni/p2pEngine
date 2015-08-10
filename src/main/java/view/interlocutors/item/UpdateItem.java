package view.interlocutors.item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class UpdateItem extends AbstractInterlocutor {

	public UpdateItem() {
		super();
	}
	
	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
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
			content.put("feedback", "Item updated");
			content.put("feedbackOk", true);
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
