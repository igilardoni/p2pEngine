package view.interlocutors;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadItemSearchFieldType extends AbstractInterlocutor {

	public LoadItemSearchFieldType() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			Item.TYPE[] types = Item.TYPE.values();
			for (Item.TYPE t : types) {
				JSONObject data = new JSONObject();
				data.put("query", "itemSearchFieldTypeLoaded");
				JSONObject content = new JSONObject();
				content.put("option", t.toString());
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
