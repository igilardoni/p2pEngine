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

import model.Application;
import model.data.user.User;
import model.network.search.Search;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.VARIABLES;
import view.interlocutors.AbstractInterlocutor;
import controller.MessageController;

public class SendMessage extends AbstractInterlocutor {

	public SendMessage() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String subject = c.getString("subject");
			String message = c.getString("message");
			String receiver = c.getString("receiver");
			
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			
			Search<User> search = new Search<User>(Application.getInstance().getNetwork(), User.class.getSimpleName(), "publicKey", true);
			search.search(receiver, VARIABLES.MaxTimeSearch, VARIABLES.ReplicationsAccount);
			
			ArrayList<User> users = search.getResults();
			if(users.isEmpty()) {
				data.put("query", "messageNotSent");
				content.put("feedback", "Account not found on network !");
				data.put("content", content);	
				com.sendText(data.toString());
			}
			long moreRecent = 0L;
			for(User u : users){
				if(moreRecent < u.getLastUpdated())
					moreRecent = u.getLastUpdated();
				else
					users.remove(u);
			}
			for(User u : users){
				if(moreRecent > u.getLastUpdated())
					users.remove(u);
			}
			User u = users.get(0);
			
			MessageController messageController = new MessageController(subject, message, u.getKeys());
			if(messageController.send()){
				data.put("query", "messageSent");
				content.put("feedback", "Message Send to "+u.getNick()+" !");
				data.put("content", content);
				com.sendText(data.toString());
			} else {
				data.put("query", "messageNotSent");
				content.put("feedback", "Message not send !");
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
