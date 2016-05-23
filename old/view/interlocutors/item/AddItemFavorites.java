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

import java.util.ArrayList;

import model.Application;
import model.data.item.Item;
import model.network.search.ItemSearcher;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class AddItemFavorites extends AbstractInterlocutor {

	public AddItemFavorites() {
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String itemKey;
			itemKey = c.getString("itemKey");
			ArrayList<String> itemsFavorites = ManagerBridge.getFavoriteItemsKey();
			if(itemsFavorites.contains(itemKey)){
				JSONObject data = new JSONObject();
				data.put("query", "favoritesItemsLoadedError");
				JSONObject content = new JSONObject();
				content.put("feedback", "This item is already in Favorites !");
				content.put("feedbackOk", false);
				data.put("content", content);
				com.sendText(data.toString());
				return;
			}
			Item item = null;
			ArrayList<Item> items = ManagerBridge.getCurrentUserItems();
			for (Item i : items) {
				if(i.getItemKey().equals(itemKey)){
					item = i;
					break;
				}
			}
			if(item == null){
				ItemSearcher itemSearcher = new ItemSearcher(Application.getInstance().getNetwork());
				item = itemSearcher.search(itemKey);
			}
			if(item == null){
				JSONObject data = new JSONObject();
				data.put("query", "favoritesItemsLoadedError");
				JSONObject content = new JSONObject();
				content.put("feedback", "This item isn't found on Network !");
				content.put("feedbackOk", false);
				data.put("content", content);
				com.sendText(data.toString());
				return;
			}
			ManagerBridge.addFavoriteItem(item);
			JSONObject data = new JSONObject();
			data.put("query", "favoritesItemsLoaded");
			JSONObject content = new JSONObject();
			content.put("itemKey", itemKey);
			content.put("title", item.getTitle());
			content.put("description", item.getDescription());
			content.put("feedback", "Item add in favorites");
			content.put("feedbackOk", true);
			data.put("content", content);
			com.sendText(data.toString());
			return;
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
