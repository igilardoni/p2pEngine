package view.interlocutors.contrat;

import model.data.contrat.Contrat;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class NewContrat extends AbstractInterlocutor {

	public NewContrat() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			JSONObject data;
			JSONObject content;
			String title = !(c.getString("title")).equals("") ? c.getString("title") : "Contract "+(ManagerBridge.getCurrentUserContrats().size()+1);
			System.out.println(title);
			Contrat contrat = ManagerBridge.createContrat(title);
			
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
