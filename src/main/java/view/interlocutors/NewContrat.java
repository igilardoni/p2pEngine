package view.interlocutors;

import javax.websocket.Session;

import model.data.contrat.Contrat;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class NewContrat extends AbstractInterlocutor {

	public NewContrat() {
	}
	
	public static String content;
	
	@Override
	public void init(String content, Session session){
		this.content = content;
		AbstractInterlocutor.com = session.getAsyncRemote();
	}
	
	@Override
	public void reset(){
		this.content = null;
	}
	
	@Override
	public boolean isInitialized(){
		return this.content != null && this.com != null;
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			JSONObject data;
			JSONObject content;
			Contrat contrat = ManagerBridge.createContrat(c.getString("title"));
			
			/* New signatory */
			/**/ data = new JSONObject();
			/**/ content = new JSONObject();
			/**/ data.put("query", "signatoryAdded");
			/**/ content.put("publicKey", ManagerBridge.getCurrentUser().getKeys().getPublicKey().toString(16));
			/**/ content.put("friendlyNick", ManagerBridge.getCurrentUser().getNick());
			/**/ data.put("content", content);
			/**/ com.sendText(data.toString());
			
			data = new JSONObject();
			data.put("query", "contratCreated");
			content = new JSONObject();
			content.put("contratID", contrat.getId());
			content.put("title", contrat.getTitle());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
