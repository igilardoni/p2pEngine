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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import model.Application;
import model.data.favorites.KnownUsers;
import view.interlocutors.AbstractInterlocutor;

public class LoadKnownUsers extends AbstractInterlocutor{

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			KnownUsers ku = Application.getInstance().getManager().getKnownUsersManager().getCurrentUserKnownUsers();
			for(String name: ku.getUsers().keySet()) {
				JSONObject data = new JSONObject();
				data.put("query", "knownUserFound");
				JSONObject content = new JSONObject();
				content.put("nickname", name);
				content.put("publicKey", ku.get(name).getPublicKey().toString(16));
				data.put("content", content);
				System.out.println("sending " + name);
				com.sendText(data.toString());
			}
			
		} catch (JSONException e) {
				e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
