package controller.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadItemSearchFieldType extends AbstractInterlocutor {

	public LoadItemSearchFieldType() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		Item.TYPE[] types = Item.TYPE.values();
		for (Item.TYPE t : types) {
			JSONObject data = new JSONObject();
			data.put("query", "itemSearchFieldTypeLoaded");
			JSONObject content = new JSONObject();
			content.put("option", t.toString());
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
