package view.interlocutors.message;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class RemoveMessage extends AbstractInterlocutor {

	public RemoveMessage() {
	}

	@Override
	public void run() { 
		if(!isInitialized())
			return;
		try {
			JSONObject c = getJSON(content);
			JSONObject data;
			String id = c.getString("id");
			String publicKey = c.getString("publicKey");
			if(ManagerBridge.removeMessage(publicKey, id)){
				data = new JSONObject();
				data.put("query", "messageRemoved");
				c.put("feedbackOk", true);
				c.put("feedback", "Message removed.");
				data.put("content", c);
				com.sendText(data.toString());
				if(ManagerBridge.getCurrentUserConversation().getConversation(publicKey).size() <= 0 ) {
					ManagerBridge.removeConversation(publicKey);
					JSONObject content = new JSONObject();
					data = new JSONObject();
					data.put("query", "conversationRemoved");
					content.put("id", publicKey);
					content.put("feedbackOk", true);
					content.put("feedback", c.getString("feedback")+"<br />Conversation removed.");
					data.put("content", content);
				}
			} else {
				data = new JSONObject();
				data.put("query", "messageNotRemoved");
				c.put("feedbackOk", false);
				c.put("feedback", "An unknown error occurred while deleting the message.");
				data.put("content", c);
				com.sendText(data.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
