package view.interlocutors.search;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.SearchItemController;
import controller.SearchUserController;
import model.data.user.User;
import model.network.search.SearchListener;
import util.AdvertisementToJson;
import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;

public class SearchUser extends AbstractInterlocutor implements SearchListener<User>{

	@Override
	public void run() {
		if(!isInitialized()) return;
		JSONObject c = getJSON(content);
		try {
			SearchUserController is = new SearchUserController();
			is.addListener(this);
			is.startSearch(c.getString("search"));
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

	@Override
	public void searchEvent(User event) {
		try {
			JSONObject data = new JSONObject();
			data.put("query", "userSearchFound");
			JSONObject content = new JSONObject();
			content.put("nick", event.getNick());
			content.put("publicKey", event.getKeys().getPublicKey().toString(16));
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
