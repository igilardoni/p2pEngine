package view.interlocutors.message;

import model.data.user.UserMessage;
import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadMessage extends AbstractInterlocutor {

	public LoadMessage() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String id = c.getString("id");
			UserMessage message = ManagerBridge.getMessage(id);
			if(message == null) {
				// TODO ERROR !!!
			}
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			if(message == null){
				data.put("query", "messageUnLoaded");
				content.put("error", "unknow message");
			}else{
				data.put("query", "messageLoaded");
				content.put("id", message.getID());
				content.put("subject", message.getSubject());
				content.put("message", message.getContent());
				content.put("date", DateConverter.getString(message.getDate()));
				content.put("sender", message.getSender().getPublicKey().toString(16));
			}
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
