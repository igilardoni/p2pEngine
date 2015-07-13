package view.interlocutors;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadType extends AbstractInterlocutor {

	public LoadType() {
	}
	
	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			Item.TYPE[] types = Item.TYPE.values();
			for (Item.TYPE type : types) {
				JSONObject data = new JSONObject();
				data.put("query", "typeLoaded");
				JSONObject content = new JSONObject();
				content.put("type", type.toString());
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
