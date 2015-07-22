package view.interlocutors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class RemoveContrat extends AbstractInterlocutor {

	public RemoveContrat() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String contratID = c.getString("contratID");
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			if(ManagerBridge.removeContrat(contratID)){
				data.put("query", "contratRemoved");
				content.put("feedback", "This contrat was been removed.");
				content.put("contratID", contratID);
				data.put("content", content);
				com.sendText(data.toString());
			} else {
				data.put("query", "contratNotRemoved");
				content.put("feedback", "Removing contrat failed !!!");
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
