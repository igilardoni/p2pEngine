package view.interlocutors;

import javax.websocket.Session;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadBase64Image extends AbstractInterlocutor {

	public LoadBase64Image() {
	}
	
	public static String content;

	@Override
	public void init(String content, Session session) {
		this.content = content;
		AbstractInterlocutor.com = session.getAsyncRemote();
	}

	@Override
	public void reset() {
		this.content = null;
	}

	@Override
	public boolean isInitialized() {
		return this.content != null && this.com != null;
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try{
			JSONObject c = getJSON(content);
			String url = c.getString("url");
			
			String img = util.ImageBase64.encode(url);
			
			JSONObject data = new JSONObject();
			data.put("query", "imageBase64Loaded");
			
			JSONObject content = new JSONObject();
			content.put("img", img);
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
