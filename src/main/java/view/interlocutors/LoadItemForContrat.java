package view.interlocutors;

import javax.websocket.Session;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadItemForContrat extends AbstractInterlocutor {

	public LoadItemForContrat() {
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
			String itemKey, contrat;
			itemKey = c.getString("itemKey");
			contrat = c.getString("contratId");
			
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			
			Item item = null;
			item = ManagerBridge.getCurrentUserItem(itemKey);
			if(item == null)
				item = ManagerBridge.getFavoriteItem(itemKey);
			if(item == null){
				data.put("query", "itemForContratNotLoaded");
				content.put("Error", "Item not in Favorites or current list");
			}else{
				ManagerBridge.addItemContrat(item, contrat);
				data.put("query", "itemForContratLoaded");
				content.put("contratId", contrat);
				content.put("itemKey", item.getItemKey());
				content.put("title", item.getTitle());
				content.put("owner", item.getOwner());
				content.put("friendlyNick", item.getFriendNick());
				content.put("description", item.getDescription());
				content.put("date", item.getDate());
				content.put("lastUpdated", item.getLastUpdated());
				content.put("category", item.getCategory().getStringChoice());
				content.put("type", item.getType().toString());
				content.put("contact", item.getContact());
				content.put("country", item.getCountry());
				content.put("image", item.getImage());
			}
			
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
