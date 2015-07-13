package view.interlocutors;

import java.util.ArrayList;

import javax.websocket.Session;

import model.data.contrat.Contrat;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadContrats extends AbstractInterlocutor {
	
	public LoadContrats() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			ArrayList<Contrat> contrats = ManagerBridge.getCurrentUserContrats();
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			for (Contrat contrat : contrats) {
				data.put("query", "contratsLoaded");
				content.put("contratID", contrat.getId());
				content.put("state", contrat.getStateStringFormat());
				content.put("title", contrat.getTitle());
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
