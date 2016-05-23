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

import model.data.contrat.Contrat;
import model.data.item.Item;
import view.interlocutors.AbstractInterlocutor;

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
				content.put("feedbackOk", true);
			} else {
				data.put("query", "itemContratNotRemoved");
				content.put("feedback", "An unknown error occurred while deleting the item.");
				content.put("feedbackOk", false);
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
					content.put("feedbackOk", true);
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
