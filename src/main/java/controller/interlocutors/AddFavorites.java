package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.Application;
import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class AddFavorites extends AbstractInterlocutor {

	public AddFavorites() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		JSONObject c = getJSON(msg);
		String itemKey = c.getString("itemKey");
		ArrayList<Item> items = ManagerBridge.getCurrentUserItems();
		for (Item item : items) {
			if(item.getItemKey().equals(itemKey)){
				ManagerBridge.addFavoriteItem(item);
				JSONObject data = new JSONObject();
				data.put("query", "favoritesItemsLoaded");
				JSONObject content = new JSONObject();
				content.put("itemKey", itemKey);
				content.put("title", item.getTitle());
				content.put("description", item.getDescription());
				data.put("content", content);
				session.getBasicRemote().sendText(data.toString());
				return;
			}
		}
		// If here, search on network the item with itemKey, for add on favorites
	}

}
