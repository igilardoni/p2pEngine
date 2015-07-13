package view.interlocutors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoadBase64Image extends AbstractInterlocutor {

	public LoadBase64Image() {
		super();
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
