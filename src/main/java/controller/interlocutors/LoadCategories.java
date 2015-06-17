package controller.interlocutors;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import model.data.item.Category;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadCategories extends AbstractInterlocutor {

	public LoadCategories() {
	}

	@Override
	public void sender(String msg, Session session) throws JSONException,
			IOException {
		ArrayList<String> listCat = Category.getAllCategorie();
		for (String string : listCat) {
			JSONObject data = new JSONObject();
			data.put("query", "categoryLoaded");
			JSONObject content = new JSONObject();
			content.put("category", string);
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

}
