package view.interlocutors;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadItemFavorites extends AbstractInterlocutor {

	public LoadItemFavorites() {
	}
	
	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String itemKey;
			itemKey = c.getString("itemKey");
			
			Item item = ManagerBridge.getFavoriteItem(itemKey);
			JSONObject data = new JSONObject();
			data.put("query", "favoritesItemLoaded");
			JSONObject content = new JSONObject();
			content.put("itemKey", item.getItemKey());
			content.put("owner", item.getOwner());
			content.put("friendlyNick", item.getFriendNick());
			content.put("title", item.getTitle());
			content.put("type", item.getType());
			content.put("category", item.getCategory().getStringChoice());
			content.put("description", item.getDescription());
			content.put("country", item.getCountry());
			content.put("contact", item.getContact());
			content.put("image", item.getImage());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
