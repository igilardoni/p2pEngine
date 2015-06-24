package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.data.item.Category;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadItemSearchFieldCategory extends AbstractInterlocutor {

	public LoadItemSearchFieldCategory() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		ArrayList<String> categories = Category.getAllCategorie();
		for (String c : categories) {
			JSONObject data = new JSONObject();
			data.put("query", "itemSearchFieldCategoryLoaded");
			JSONObject content = new JSONObject();
			content.put("option", c);
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
