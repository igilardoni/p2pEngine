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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class RemoveMessage extends AbstractInterlocutor {

	public RemoveMessage() {
	}

	@Override
	public void run() { 
		if(!isInitialized())
			return;
		try {
			JSONObject c = getJSON(content);
			JSONObject data;
			String id = c.getString("id");
			String publicKey = c.getString("publicKey");
			if(ManagerBridge.removeMessage(publicKey, id)){
				data = new JSONObject();
				data.put("query", "messageRemoved");
				c.put("feedbackOk", true);
				c.put("feedback", "Message removed.");
				data.put("content", c);
				com.sendText(data.toString());
				if(ManagerBridge.getCurrentUserConversation().getConversation(publicKey).size() <= 0 ) {
					ManagerBridge.removeConversation(publicKey);
					JSONObject content = new JSONObject();
					data = new JSONObject();
					data.put("query", "conversationRemoved");
					content.put("id", publicKey);
					content.put("feedbackOk", true);
					content.put("feedback", c.getString("feedback")+"<br />Conversation removed.");
					data.put("content", content);
					com.sendText(data.toString());
				}
			} else {
				data = new JSONObject();
				data.put("query", "messageNotRemoved");
				c.put("feedbackOk", false);
				c.put("feedback", "An unknown error occurred while deleting the message.");
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
