package view.interlocutors;

import model.data.item.Item;
import model.network.search.SearchListener;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.DateConverter;
import controller.SearchItemController;

public class SearchItem extends AbstractInterlocutor implements SearchListener<Item> {

	public SearchItem(){
		super();
	}
	
	@Override
	public void run() {
		if(!isInitialized()) return;
		JSONObject c = getJSON(content);
		try {
			SearchItemController is = new SearchItemController();
			is.addListener(this);
			is.startSearch(c.getString("search"));
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

	@Override
	public void searchEvent(Item event) {
		try {
			JSONObject data = new JSONObject();
			data.put("query", "itemSearchFound");
			JSONObject content = new JSONObject();
			content.put("itemKey", event.getItemKey());
			content.put("title", event.getTitle());
			content.put("description", event.getDescription());
			content.put("category", event.getCategory().getStringChoice());
			content.put("contact", event.getContact());
			content.put("country", event.getCountry());
			content.put("image", event.getImage());
			content.put("lifetime", DateConverter.getString(event.getLifeTime()+event.getDate()));
			content.put("type", event.getType());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
