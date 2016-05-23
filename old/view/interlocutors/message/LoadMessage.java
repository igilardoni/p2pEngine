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

import model.data.user.UserMessage;
import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadMessage extends AbstractInterlocutor {

	public LoadMessage() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String id = c.getString("id");
			UserMessage message = ManagerBridge.getMessage(id);
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			if(message == null){
				data.put("query", "messageUnLoaded");
				content.put("error", "unknow message");
			}else{
				data.put("query", "messageLoaded");
				content.put("id", message.getId());
				content.put("subject", message.getSubject());
				content.put("message", message.getContent());
				content.put("date", DateConverter.getString(message.getDate()));
				content.put("sender", message.getSender().getPublicKey().toString(16));
			}
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
