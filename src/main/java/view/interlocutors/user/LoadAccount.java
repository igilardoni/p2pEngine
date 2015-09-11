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
package view.interlocutors.user;

import model.data.user.User;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadAccount extends AbstractInterlocutor {

	public LoadAccount() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			User user = ManagerBridge.getCurrentUser();
			JSONObject data = new JSONObject();
			data.put("query", "accountLoaded");
			
			JSONObject content = new JSONObject();
			content.put("username", user.getNick());
			content.put("name", user.getName());
			content.put("firstname", user.getFirstName());
			content.put("phone", user.getPhone());
			content.put("email", user.getEmail());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
				e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
