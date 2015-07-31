package view.interlocutors;

import model.data.contrat.Clause;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class AddClause extends AbstractInterlocutor {

	public AddClause() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			JSONObject c = getJSON(content);
			String contratID = c.getString("contratID");
			Clause clause = ManagerBridge.addClause(contratID);
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			data.put("query", "clauseAdded");
			content.put("id", clause.getId());
			content.put("title", clause.getTitle());
			content.put("value", clause.getContent());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
