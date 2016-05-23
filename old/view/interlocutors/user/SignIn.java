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

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class SignIn extends AbstractInterlocutor {
	
	public SignIn() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			if(ManagerBridge.login((String) c.getString("username"), (String) c.getString("password"))){
				JSONObject data = new JSONObject();
				data.put("query", "login");
				JSONObject content = new JSONObject();
				content.put("ok", "ok");
				content.put("feedback", "user logged");
				content.put("feedbackOk", true);
				content.put("username", c.getString("username"));
				data.put("content", content);
				com.sendText(data.toString());
			}else{
				JSONObject data = new JSONObject();
				data.put("query", "login");
				JSONObject content = new JSONObject();
				content.put("feedbackOk", false);
				content.put("feedback", "Can't connect. Thank you to verify that you correctly entered your username and password.");
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
