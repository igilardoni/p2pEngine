package view.interlocutors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class RenameContrat extends AbstractInterlocutor {

	public RenameContrat() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String title = c.getString("title");
			String contratID = c.getString("contratID");
			if(ManagerBridge.renameContrat(contratID, title)){
				JSONObject content = new JSONObject();
				JSONObject data = new JSONObject();
				content.put("contratID", contratID);
				content.put("title", title);
				content.put("feedback", "Contract renamed");
				data.put("query", "contractRenamed");
				data.put("content", content);
				com.sendText(data.toString());
			} else {
				// TODO query contractNotRenamed !
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
