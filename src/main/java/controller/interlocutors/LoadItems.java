package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadItems extends AbstractInterlocutor {

	public LoadItems() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		ArrayList<Item> items = ManagerBridge.getCurrentUserItems();
		if(items == null || items.isEmpty()) return;
		for (Item item : items) {
			JSONObject data = new JSONObject();
			data.put("query", "itemsLoaded");
			JSONObject content = new JSONObject();
			content.put("itemKey", item.getItemKey());
			content.put("title", item.getTitle());
			content.put("description", item.getDescription());
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
