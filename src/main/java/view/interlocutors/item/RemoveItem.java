package view.interlocutors.item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class RemoveItem extends AbstractInterlocutor {

	public RemoveItem() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String itemKey = c.getString("itemKey");
			ManagerBridge.removeItem(itemKey);
			JSONObject data = new JSONObject();
			data.put("query", "itemRemoved");
			JSONObject content = new JSONObject();
			content.put("itemKey", itemKey);
			content.put("feedback", "Item removed");
			content.put("feedbackOk", true);
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
