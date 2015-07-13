package view.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadItemsFavorites extends AbstractInterlocutor {

	public LoadItemsFavorites() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			ArrayList<Item> favorites = ManagerBridge.getFavoriteItems();
			// to say to Javascript that Model start to found favorites' items
			sendStart();
		
			for (Item item : favorites) {
				JSONObject data = new JSONObject();
				data.put("query", "favoritesItemsLoaded");
				JSONObject content = new JSONObject();
				content.put("itemKey", item.getItemKey());
				content.put("title", item.getTitle());
				content.put("description", item.getDescription());
				data.put("content", content);
				com.sendText(data.toString());
			}
			// to say to Javascript that Model finish to found favorites' items
			sendEnd();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}
	
	private void sendStart() {
		try{
			JSONObject data = new JSONObject();
			data.put("query", "favoritesItemsLoadingStart");
			JSONObject content = new JSONObject();
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void sendEnd() {
		try{
			JSONObject data = new JSONObject();
			data.put("query", "favoritesItemsLoadingEnd");
			JSONObject content = new JSONObject();
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
