package view.interlocutors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.MessageSender;

public class SendMessage extends AbstractInterlocutor {

	public SendMessage() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String subject = c.getString("subject");
			String message = c.getString("message");
			String receiver = c.getString("receiver");
			String type = c.getString("typeReceiver");
			
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			
			boolean ok = false;
			
			if(type.equals("Username")) ok = MessageSender.sendMessageToNick(message, receiver);
			if(type.equals("PublicKey"))
				try {
					ok = MessageSender.sendMessageToPublicKey(message, receiver);
				} catch (Exception e) {
					content.put("error", "Account not found on Network");
					data.put("query", "messageNotSent");
					data.put("content", content);	
					com.sendText(data.toString());
				}
			
			if(ok){
				data.put("query", "messageSent");
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
