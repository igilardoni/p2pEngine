package view.interlocutors;

import javax.websocket.RemoteEndpoint.Async;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public abstract class AbstractInterlocutor {
	public static Async com;
	protected String content;

	public static void setCom(Async com){
		AbstractInterlocutor.com = com;
	}
	
	public void init(String content){
		this.content = content;
	}
	
	public void reset(){
		this.content = null;
	}
	
	public boolean isInitialized(){
		return this.content != null && AbstractInterlocutor.com != null;
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
