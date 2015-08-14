package view.interlocutors.message;

import java.util.ArrayList;

import model.data.user.Conversations;
import model.data.user.UserMessage;
import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadConversation extends AbstractInterlocutor {

	public LoadConversation() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
			try {
				JSONObject c = getJSON(content);
				String publicKey = c.getString("publicKey");
				ArrayList<UserMessage> messages = ManagerBridge.getCurrentUserConversation().getConversation(publicKey);
				for (UserMessage message : messages) {
					// TODO Maybe try to sort by date ?
					JSONObject data = new JSONObject();
					JSONObject content = new JSONObject();
					data.put("query", "conversationMessageLoaded");
					content.put("id", message.getID());
					content.put("message", message.getContent());
					content.put("date", DateConverter.getString(message.getDate()));
					content.put("sender", message.getSender().getPublicKey().toString(16));
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
