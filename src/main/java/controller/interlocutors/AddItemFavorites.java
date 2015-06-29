package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.Application;
import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class AddItemFavorites extends AbstractInterlocutor {

	public AddItemFavorites() {
	}
	
	public static String content;
	
	@Override
	public void init(String content, Session session){
		this.content = content;
		AbstractInterlocutor.com = session.getAsyncRemote();
	}
	
	@Override
	public void reset(){
		this.content = null;
	}
	
	@Override
	public boolean isInitialized(){
		return this.content != null && this.com != null;
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String itemKey;
			itemKey = c.getString("itemKey");
		
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
					com.sendText(data.toString());
					return;
				}
			}
			// If here, search on network the item with itemKey, for add on favorites
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
