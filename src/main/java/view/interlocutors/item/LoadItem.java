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
package view.interlocutors.item;

import model.data.item.Item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.DateConverter;
import view.interlocutors.AbstractInterlocutor;
import controller.ManagerBridge;

public class LoadItem extends AbstractInterlocutor {

	public LoadItem() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String itemKey;
			itemKey = c.getString("itemKey");
			
			Item item = ManagerBridge.getCurrentUserItem(itemKey);
			JSONObject data = new JSONObject();
			data.put("query", "itemLoaded");
			JSONObject content = new JSONObject();
			content.put("itemKey", itemKey);
			content.put("title", item.getTitle());
			content.put("description", item.getDescription());
			content.put("category", item.getCategory().getStringChoice());
			content.put("contact", item.getContact());
			content.put("country", item.getCountry());
			content.put("image", item.getImage());
			content.put("lifetime", DateConverter.getString(item.getLifeTime()+item.getDate()));
			content.put("type", item.getType());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
