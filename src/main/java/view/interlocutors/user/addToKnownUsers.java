package view.interlocutors.user;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.SearchUserController;
import model.Application;
import model.data.favorites.KnownUsers;
import model.data.user.User;
import util.Printer;
import view.interlocutors.AbstractInterlocutor;

public class addToKnownUsers extends AbstractInterlocutor {

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String nick = c.getString("nick");
			String publicKey = c.getString("publickey");
			KnownUsers ku = Application.getInstance().getManager().getKnownUsersManager().getCurrentUserKnownUsers();
			SearchUserController su = new SearchUserController();
			su.startSearchByPublicKey(publicKey);
			User result = su.getPublicKeyResult();
			if(result == null) {
				Printer.printError(this, "run", "no result");
			}
			else {
				ku.add(nick, result);
			}
			
		} catch (JSONException e) {
				e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
