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
package util;

import java.util.HashMap;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import model.advertisement.AbstractAdvertisement;

public class AdvertisementToJson {
	public static JSONObject AdvertisementToJSON(AbstractAdvertisement adv) {
		JSONObject data = new JSONObject();
		HashMap<String, String> keys = adv.getKeysValues();
		for(String key: keys.keySet()) {
			try {
				data.put(key, keys.get(key));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
}
