package view.interlocutors;

import java.util.ArrayList;

import javax.websocket.Session;

import model.data.contrat.Contrat;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadContrats extends AbstractInterlocutor {
	
	public LoadContrats() {
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
			ArrayList<Contrat> contrats = ManagerBridge.getCurrentUserContrats();
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			for (Contrat contrat : contrats) {
				data.put("query", "contratsLoaded");
				content.put("id", contrat.getId());
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
