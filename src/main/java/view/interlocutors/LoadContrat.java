package view.interlocutors;

import java.util.ArrayList;

import model.data.contrat.Contrat;
import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadContrat extends AbstractInterlocutor {
	
	public LoadContrat() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String id;
			id = c.getString("contratID");
			
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			Contrat contrat = ManagerBridge.getCurrentUserContrat(id);
			if(contrat == null){
				data.put("query", "contratNotLoaded");
				content.put("feedback", "Contrat doesn't exist !");
				data.put("content", content);
				com.sendText(data.toString());
			}else{
				data.put("query", "contratLoaded");
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
