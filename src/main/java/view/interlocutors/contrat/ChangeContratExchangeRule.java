package view.interlocutors.contrat;

import model.data.contrat.Contrat;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class ChangeContratExchangeRule extends AbstractInterlocutor {

	public ChangeContratExchangeRule() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			JSONObject c = getJSON(content);
			String contratID = c.getString("contratID");
			String itemKey = c.getString("itemKey");
			String itemTitle = c.getString("itemTitle");
			String from = c.getString("from");
			String fromNick = c.getString("fromFriendlyNick");
			String to = c.getString("to");
			String toNick = c.getString("toFriendlyNick");
			
			JSONObject content = new JSONObject();
			JSONObject data = new JSONObject();
			//if(from.equals(to)){
				//System.err.println("FROM EQUALS TO");
				// TODO feedback error
			//}else{
				if(ManagerBridge.changeRuleContrat(contratID, itemKey, from, to)){
					data.put("query", "ruleContratChanged");
					content.put("contratID", contratID);
					content.put("from", from);
					content.put("fromFriendlyNick", fromNick);
					content.put("to", to);
					content.put("toFriendlyNick", toNick);
					content.put("itemKey", itemKey);
					content.put("itemTitle", itemTitle);
					data.put("content", content);
					com.sendText(data.toString());
				} else {
					// TODO feedback FAIL !
					System.err.println("ERROR RULE");
				}
			//}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
