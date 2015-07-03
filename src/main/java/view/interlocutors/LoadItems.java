package view.interlocutors;

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
				com.sendText(data.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}