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

import model.data.user.Conversations;
import model.data.user.UserMessage;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;

public class LoadConversations extends AbstractInterlocutor {

	public LoadConversations() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			Conversations conversation = ManagerBridge.getCurrentUserConversation();
			ArrayList<String> senders = conversation.getSenders();
			for(String s : senders) {
				long date = 0;
				boolean isRead = true;
				for(UserMessage message : conversation.getConversation(s)) {
					date = date < message.getDate() ? message.getDate() : date;
					isRead = isRead && message.isRead();
				}
				JSONObject data = new JSONObject();
				JSONObject content = new JSONObject();
				data.put("query", "conversationsLoaded");
				content.put("id", s);
				content.put("sender", s);
				content.put("date", DateConverter.getString(date));
				content.put("isRead", isRead);
				content.put("count", conversation.getConversation(s).size());
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
