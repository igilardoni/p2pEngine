package view.interlocutors;

import java.util.ArrayList;

import model.data.item.Category;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadCategories extends AbstractInterlocutor {

	public LoadCategories() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
		ArrayList<String> listCat = Category.getAllCategorie();
			for (String string : listCat) {
				JSONObject data = new JSONObject();
				data.put("query", "categoryLoaded");
				
				JSONObject content = new JSONObject();
				content.put("category", string);
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
