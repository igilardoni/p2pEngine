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
