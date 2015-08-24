package view.interlocutors.user;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import model.Application;
import model.data.favorites.KnownUsers;
import view.interlocutors.AbstractInterlocutor;

public class LoadKnownUsers extends AbstractInterlocutor{

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			KnownUsers ku = Application.getInstance().getManager().getKnownUsersManager().getCurrentUserKnownUsers();
			for(String name: ku.getUsers().keySet()) {
				JSONObject data = new JSONObject();
				data.put("query", "knownUserFound");
				JSONObject content = new JSONObject();
				content.put("nickname", name);
				content.put("publicKey", ku.get(name).getPublicKey().toString(16));
				data.put("content", content);
				System.out.println("sending " + name);
				com.sendText(data.toString());
			}
			
		} catch (JSONException e) {
				e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
