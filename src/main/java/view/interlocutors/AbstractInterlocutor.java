package view.interlocutors;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public abstract class AbstractInterlocutor extends Thread {
	public Async com;
	protected String content;
	
	
	public void init(String content, Session session){
		this.content = content;
		com = session.getAsyncRemote();
	}
	
	public void reset(){
		this.content = null;
	}
	
	public boolean isInitialized(){
		return this.content != null && this.com != null;
	}
	
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
