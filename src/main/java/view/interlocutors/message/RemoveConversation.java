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
package view.interlocutors.message;

import java.util.ArrayList;

import model.data.user.UserMessage;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class RemoveConversation extends AbstractInterlocutor {

	public RemoveConversation() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			JSONObject c = getJSON(content);
			JSONObject data;
			String publicKey = c.getString("id");
			
			ArrayList<String> ids = new ArrayList<String>();
			for (UserMessage m : ManagerBridge.getCurrentUserConversation().getConversation(publicKey)) {
				ids.add(m.getId());
			}
			if(ManagerBridge.removeConversation(publicKey)){
				for (String id : ids) { // If conversation is remove, have to remove all message inside :
					data = new JSONObject();
					data.put("query", "messageRemoved");
					JSONObject content = new JSONObject();
					content.put("id", id);
					data.put("content", content);
					com.sendText(data.toString());
				}
				data = new JSONObject();
				data.put("query", "conversationRemoved");
				c.put("feedback", "Conversation removed.");
				c.put("feedbackOk", true);
				data.put("content", c);
				com.sendText(data.toString());
			} else {
				data = new JSONObject();
				data.put("query", "conversationNotRemoved");
				c.put("feedback", "An unknown error occurred while deleting the conversation.");
				c.put("feedbackOk", false);
				data.put("content", c);
				com.sendText(data.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
