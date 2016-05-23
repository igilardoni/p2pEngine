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
package view.interlocutors.search;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.SearchItemController;
import controller.SearchUserController;
import model.data.user.User;
import model.network.search.SearchListener;
import util.AdvertisementToJson;
import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;

public class SearchUser extends AbstractInterlocutor implements SearchListener<User>{

	@Override
	public void run() {
		if(!isInitialized()) return;
		JSONObject c = getJSON(content);
		try {
			SearchUserController is = new SearchUserController();
			is.addListener(this);
			is.startSearch(c.getString("search"));
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

	@Override
	public void searchEvent(User event) {
		try {
			JSONObject data = new JSONObject();
			data.put("query", "userSearchFound");
			JSONObject content = new JSONObject();
			content.put("nick", event.getNick());
			content.put("publicKey", event.getKeys().getPublicKey().toString(16));
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
