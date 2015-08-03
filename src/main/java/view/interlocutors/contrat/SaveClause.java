package view.interlocutors.contrat;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class SaveClause extends AbstractInterlocutor {

	public SaveClause() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			JSONObject c = getJSON(content);
			String contratID = c.getString("contratID");
			String id = c.getString("id");
			String title = c.getString("title");
			String value = c.getString("value");
			if(ManagerBridge.saveClause(contratID, id, title, value)){
				JSONObject data = new JSONObject();
				data.put("query", "clauseSaved");
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
