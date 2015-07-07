package view.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.DateConverter;
import controller.ManagerBridge;

public class LoadItem extends AbstractInterlocutor {

	public LoadItem() {
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
			
			Item item = ManagerBridge.getCurrentUserItem(itemKey);
			JSONObject data = new JSONObject();
			data.put("query", "itemLoaded");
			JSONObject content = new JSONObject();
			content.put("itemKey", itemKey);
			content.put("title", item.getTitle());
			content.put("description", item.getDescription());
			content.put("category", item.getCategory().getStringChoice());
			content.put("contact", item.getContact());
			content.put("country", item.getCountry());
			content.put("image", item.getImage());
			content.put("lifetime", DateConverter.getString(item.getLifeTime()+item.getDate()));
			content.put("type", item.getType());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
