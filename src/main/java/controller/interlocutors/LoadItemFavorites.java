package controller.interlocutors;

import javax.websocket.Session;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadItemFavorites extends AbstractInterlocutor {

	public LoadItemFavorites() {
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
			
			Item item = ManagerBridge.getFavoriteItem(itemKey);
			JSONObject data = new JSONObject();
			data.put("query", "favoritesItemLoaded");
			JSONObject content = new JSONObject();
			content.put("itemKey", item.getItemKey());
			content.put("title", item.getTitle());
			content.put("type", item.getType());
			content.put("category", item.getCategory().getStringChoice());
			content.put("description", item.getDescription());
			content.put("country", item.getCountry());
			content.put("contact", item.getContact());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
