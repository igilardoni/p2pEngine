package view.interlocutors.item;

import java.util.ArrayList;

import model.Application;
import model.data.item.Item;
import model.network.search.ItemSearcher;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class AddItemFavorites extends AbstractInterlocutor {

	public AddItemFavorites() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String itemKey;
			itemKey = c.getString("itemKey");
			ArrayList<String> itemsFavorites = ManagerBridge.getFavoriteItemsKey();
			if(itemsFavorites.contains(itemKey)){
				JSONObject data = new JSONObject();
				data.put("query", "favoritesItemsLoadedError");
				JSONObject content = new JSONObject();
				content.put("message", "This item is already in Favorites !");
				data.put("content", content);
				com.sendText(data.toString());
				return;
			}
			Item item = null;
			ArrayList<Item> items = ManagerBridge.getCurrentUserItems();
			for (Item i : items) {
				if(i.getItemKey().equals(itemKey)){
					item = i;
					break;
				}
			}
			if(item == null){
				ItemSearcher itemSearcher = new ItemSearcher(Application.getInstance().getNetwork());
				item = itemSearcher.search(itemKey);
			}
			if(item == null){
				JSONObject data = new JSONObject();
				data.put("query", "favoritesItemsLoadedError");
				JSONObject content = new JSONObject();
				content.put("message", "This item isn't found on Network !");
				data.put("content", content);
				com.sendText(data.toString());
				return;
			}
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
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
