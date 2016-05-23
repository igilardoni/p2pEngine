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
