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

import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;
import controller.ManagerBridge;

public class LoadConversation extends AbstractInterlocutor {

	public LoadConversation() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
			try {
				JSONObject c = getJSON(content);
				String publicKey = c.getString("id");
				ArrayList<UserMessage> messages = ManagerBridge.getCurrentUserConversation().getConversation(publicKey);
				for (UserMessage message : messages) {
					// TODO Maybe try to sort by date ?
					JSONObject data = new JSONObject();
					JSONObject content = new JSONObject();
					data.put("query", "conversationLoaded");
					content.put("conversation", publicKey);
					content.put("id", message.getId());
					content.put("message", message.getContent());
					content.put("subject", message.getSubject());
					content.put("date", DateConverter.getString(message.getDate()));
					content.put("sender", message.getSender().getPublicKey().toString(16));
					content.put("receiver", message.getReceiver().getPublicKey().toString(16));
					content.put("isRead", message.isRead());
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
