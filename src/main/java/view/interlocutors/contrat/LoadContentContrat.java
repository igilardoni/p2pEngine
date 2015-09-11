/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package view.interlocutors.contrat;

import java.util.ArrayList;

import model.data.contrat.Clause;
import model.data.contrat.Contrat;
import model.data.item.Item;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadContentContrat extends AbstractInterlocutor {

	public LoadContentContrat() {
		// TODO Auto-generated constructor stub
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
			ArrayList<String> signatoriesAlreadySent = new ArrayList<String>();
			
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
			}
			for(Clause clause : contrat.getClauses()){
				data = new JSONObject();
				content = new JSONObject();
				data.put("query", "clauseLoaded");
				content.put("title", clause.getTitle());
				content.put("value", clause.getContent());
				content.put("id", clause.getId());
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
