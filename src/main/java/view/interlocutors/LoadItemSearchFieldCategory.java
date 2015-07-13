package view.interlocutors;

import java.util.ArrayList;

import model.data.item.Category;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadItemSearchFieldCategory extends AbstractInterlocutor {

	public LoadItemSearchFieldCategory() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try{
			ArrayList<String> categories = Category.getAllCategorie();
			for (String c : categories) {
				JSONObject data = new JSONObject();
				data.put("query", "itemSearchFieldCategoryLoaded");
				JSONObject content = new JSONObject();
				content.put("option", c);
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
