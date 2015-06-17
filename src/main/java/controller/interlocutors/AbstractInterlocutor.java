package controller.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public abstract class AbstractInterlocutor {
	public abstract void sender(String msg, Session session) throws JSONException, IOException;

	protected static JSONObject getJSON(String string){
		try {
			return new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
