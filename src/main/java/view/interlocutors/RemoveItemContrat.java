package view.interlocutors;

import java.util.ArrayList;

import model.data.contrat.Contrat;
import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class RemoveItemContrat extends AbstractInterlocutor {

	public RemoveItemContrat() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String itemKey = c.getString("itemKey");
			String contratID = c.getString("contratID");
			
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			
			if(ManagerBridge.removeItemContrat(itemKey, contratID)){
				data.put("query", "itemContratRemoved");
				content.put("contratID", contratID);
				content.put("itemKey", itemKey);
				content.put("feedback", "Item removed !");
			} else {
				data.put("query", "itemContratNotRemoved");
				content.put("feedback", "An unknown error occurred while deleting the item.");
			}
			data.put("content", content);
			com.sendText(data.toString());
			
			Contrat contract = ManagerBridge.getCurrentUserContrat(contratID);
			ArrayList<String> users = contract.getSignatories();
			for(Item i : contract.getItems()){
				if(users.contains(i.getOwner()))
					users.remove(i.getOwner());
			}
			for(String s : users){
				if(ManagerBridge.removeItemContrat(itemKey, contratID)){
					data = new JSONObject();
					content = new JSONObject();
					data.put("query", "signatoryRemoved");
					content.put("feedback", "Item removed !"+ "<br />" +"One signatory removed.");
					content.put("contratID", contratID);
					content.put("publicKey", s);
					data.put("content", content);
					com.sendText(data.toString());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
