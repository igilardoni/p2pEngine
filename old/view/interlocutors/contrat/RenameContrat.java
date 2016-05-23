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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

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
				content.put("feedbackOk", true);
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
