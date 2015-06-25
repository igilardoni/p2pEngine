package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadItemsFavorites extends AbstractInterlocutor {

	public LoadItemsFavorites() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		ArrayList<Item> favorites = ManagerBridge.getFavoriteItems();
		// to say to Javascript that Model start to found favorites' items
		sendStart(session);
		for (Item item : favorites) {
			JSONObject data = new JSONObject();
			data.put("query", "favoritesItemsLoaded");
			JSONObject content = new JSONObject();
			content.put("itemKey", item.getItemKey());
			content.put("title", item.getTitle());
			content.put("description", item.getDescription());
			data.put("content", content);
			session.getAsyncRemote().sendText(data.toString());
		}
		// to say to Javascript that Model finish to found favorites' items
		sendEnd(session);
	}
	
	private void sendStart(Session session) throws JSONException, IOException{
		JSONObject data = new JSONObject();
		data.put("query", "favoritesItemsLoadingStart");
		JSONObject content = new JSONObject();
		data.put("content", content);
		session.getAsyncRemote().sendText(data.toString());
	}
	
	private void sendEnd(Session session) throws JSONException, IOException{
		JSONObject data = new JSONObject();
		data.put("query", "favoritesItemsLoadingEnd");
		JSONObject content = new JSONObject();
		data.put("content", content);
		session.getBasicRemote().sendText(data.toString());
	}

}
