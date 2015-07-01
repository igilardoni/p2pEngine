package view.interlocutors;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public abstract class AbstractInterlocutor extends Thread {
	public static Async com;
	
	public abstract void init(String content, Session session);
	
	public abstract void reset();
	
	public abstract boolean isInitialized();
	
	@Override
	public abstract void run();

	protected static JSONObject getJSON(String string){
		try {
			return new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
