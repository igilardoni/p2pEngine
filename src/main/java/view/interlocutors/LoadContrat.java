package view.interlocutors;

import java.util.ArrayList;

import javax.websocket.Session;

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
				content.put("error", "Contrat doesn't exist !");
				data.put("content", content);
				com.sendText(data.toString());
			}else{
				ArrayList<String> signatoriesAlreadySent = new ArrayList<String>();
				data.put("query", "contratLoaded");
				content.put("contratID", contrat.getId());
				content.put("state", contrat.getStateStringFormat());
				content.put("title", contrat.getTitle());
				data.put("content", content);
				com.sendText(data.toString());
				
				signatoriesAlreadySent.add(ManagerBridge.getCurrentUser().getKeys().getPublicKey().toString(16));
				data = new JSONObject();
				content = new JSONObject();
				data.put("query", "signatoryAdded");
				content.put("publicKey", ManagerBridge.getCurrentUser().getKeys().getPublicKey().toString(16));
				content.put("friendlyNick", ManagerBridge.getCurrentUser().getNick());
				data.put("content", content);
				com.sendText(data.toString());
				
				for(Item item : contrat.getItems()){
					data = new JSONObject();
					content = new JSONObject();
					data.put("query", "itemForContratLoaded");
					content.put("title", item.getTitle());
					content.put("category", item.getCategory().getStringChoice());
					content.put("contact", item.getContact());
					content.put("contratID", contrat);
					content.put("country", item.getCountry());
					content.put("description", item.getDescription());
					content.put("date", item.getDate());
					content.put("friendlyNick", item.getFriendNick());
					content.put("image", item.getImage());
					content.put("itemKey", item.getItemKey());
					content.put("lastUpdated", item.getLastUpdated());
					content.put("lifetime", item.getLifeTime());
					content.put("owner", item.getOwner());
					content.put("type", item.getType());
					data.put("content", content);
					com.sendText(data.toString());
				}
				for(Item item : contrat.getItems()){
					data = new JSONObject();
					content = new JSONObject();
					data.put("query", "transfertRuleLoaded");
					content.put("from", item.getOwner());
					content.put("fromFriendlyNick", item.getFriendNick());
					content.put("to", contrat.getRecipientOf(item));
					content.put("itemKey", item.getItemKey());
					content.put("itemTitle", item.getTitle());
					data.put("content", content);
					com.sendText(data.toString());
					
					if(!signatoriesAlreadySent.contains(item.getOwner())){
						signatoriesAlreadySent.add(item.getOwner());
						data = new JSONObject();
						content = new JSONObject();
						data.put("query", "signatoryAdded");
						content.put("publicKey", item.getOwner());
						content.put("friendlyNick", item.getFriendNick());
						data.put("content", content);
						com.sendText(data.toString());
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
