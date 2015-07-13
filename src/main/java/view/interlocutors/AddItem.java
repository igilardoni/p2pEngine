package view.interlocutors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class AddItem extends AbstractInterlocutor {
	
	public AddItem() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String category;
			category = c.getString("category");
		
			String contact = c.getString("contact");
			String country = c.getString("country");
			String description = c.getString("description");
			String image = c.getString("image");
			String lifeTime = c.getString("lifetime");
			String title = c.getString("title");
			String type = c.getString("type");
			String itemKey = ManagerBridge.addItem(title, category, description, image, country, contact, lifeTime, type);
			
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
				com.sendText(data.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
