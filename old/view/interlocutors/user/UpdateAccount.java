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

public class UpdateAccount extends AbstractInterlocutor {

	public UpdateAccount() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String nick = c.getString("username");
			String oldPassword = c.getString("oldpassword");
			String newPassword = c.getString("password");
			String name = c.getString("name");
			String firstName = c.getString("firstname");
			String email = c.getString("email");
			String phone = c.getString("phone");
			boolean ok = ManagerBridge.updateAccount(nick, oldPassword, newPassword, name, firstName, email, phone);
			if(!ok){
				// Send error message
			}else{
				JSONObject data = new JSONObject();
				data.put("query", "accountUpdated");
				JSONObject content = new JSONObject();
				content.put("feedback", "Account updated !");
				content.put("feedbackOk", true);
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
