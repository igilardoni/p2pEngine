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

import model.data.contrat.Contrat;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class NewContrat extends AbstractInterlocutor {

	public NewContrat() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			JSONObject data;
			JSONObject content;
			String title = !(c.getString("title")).equals("") ? c.getString("title") : "Contract "+(ManagerBridge.getCurrentUserContrats().size()+1);
			System.out.println(title);
			Contrat contrat = ManagerBridge.createContrat(title);
			
			/* New signatory */
			/**/ data = new JSONObject();
			/**/ content = new JSONObject();
			/**/ data.put("query", "signatoryAdded");
			/**/ content.put("publicKey", ManagerBridge.getCurrentUser().getKeys().getPublicKey().toString(16));
			/**/ content.put("friendlyNick", ManagerBridge.getCurrentUser().getNick());
			/**/ data.put("content", content);
			/**/ com.sendText(data.toString());
			
			data = new JSONObject();
			data.put("query", "contratCreated");
			content = new JSONObject();
			content.put("contratID", contrat.getId());
			content.put("title", contrat.getTitle());
			content.put("state", contrat.getStateStringFormat());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
