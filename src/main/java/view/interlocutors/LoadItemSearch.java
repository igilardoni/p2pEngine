package view.interlocutors;

import model.Application;
import model.data.item.Item;
import model.network.search.ItemSearcher;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadItemSearch extends AbstractInterlocutor {

	public LoadItemSearch() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String itemKey = c.getString("itemKey");
			ItemSearcher is = new ItemSearcher(Application.getInstance().getNetwork());
			Item item = is.search(itemKey);
			
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			
			if(item == null) {
				data.put("query", "itemSearchNotLoaded");
				content.put("feedback", "Item not found on network !");
				content.put("itemKey", itemKey);
				data.put("content", content);
				com.sendText(data.toString());
			} else {
				data.put("query", "itemSearchLoaded");
				content.put("feedback", "Item "+item.getTitle()+" found !");
				content.put("itemKey", item.getItemKey());
				content.put("owner", item.getOwner());
				content.put("friendlyNick", item.getFriendNick());
				content.put("title", item.getTitle());
				content.put("type", item.getType());
				content.put("category", item.getCategory().getStringChoice());
				content.put("description", item.getDescription());
				content.put("country", item.getCountry());
				content.put("contact", item.getContact());
				content.put("image", item.getImage());
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
