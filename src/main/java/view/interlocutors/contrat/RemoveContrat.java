package view.interlocutors.contrat;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class RemoveContrat extends AbstractInterlocutor {

	public RemoveContrat() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String contratID = c.getString("contratID");
			String title = ManagerBridge.getCurrentUserContrat(contratID).getTitle();
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			if(ManagerBridge.removeContrat(contratID)){
				data.put("query", "contratRemoved");
				content.put("feedback", "Contract \""+title+"\" has been removed.");
				content.put("feedbackOk", true);
				content.put("contratID", contratID);
				data.put("content", content);
				com.sendText(data.toString());
			} else {
				data.put("query", "contratNotRemoved");
				content.put("feedback", "Removing contract \""+title+"\" failed !!!");
				content.put("feedbackOk", false);
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
