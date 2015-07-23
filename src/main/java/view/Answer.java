package view;

import org.codehaus.jettison.json.JSONObject;
import javax.websocket.RemoteEndpoint.Async;

public class Answer extends Thread {
	public static Async com = null;
	public JSONObject data = null;
	
	public static void setCom(Async com){
		Answer.com = com;
	}
	
	private Answer(JSONObject data) {
		this.data = data;
	}
	
	public static void reply(JSONObject data) throws Exception {
		if(com == null) throw new Exception("Answer not initialized");
		(new Answer(data)).start();
	}
	
	@Override
	public void run() {
		com.sendText(data.toString());
	}
}
