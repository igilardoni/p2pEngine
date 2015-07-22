package view.interlocutors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.data.RendezVousIp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class SponsorBootstrap extends AbstractInterlocutor {

	public SponsorBootstrap() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		JSONObject c = getJSON(content);
		try {
			RendezVousIp rdvIp = new RendezVousIp();
			File file = new File("./bootstrap.xml");
			if(!file.exists())
				file.createNewFile();
			else {
				SAXBuilder builder = new SAXBuilder();
				Document document = (Document) builder.build(file);
				Element root = document.getRootElement();
				rdvIp = new RendezVousIp(root);
			}
			FileWriter fstream = new FileWriter(file.getAbsoluteFile());
		    BufferedWriter out = new BufferedWriter(fstream);
			String[] ips = c.getString("ips").split("[\n ]");
			for(String ip : ips) {
				if(isIP(ip)) {
					rdvIp.addIp(ip);
				}
			}
			out.write(rdvIp.toString());
			out.close();
			// Answer
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			data.put("query", "sponsorBootstrapSaved");
			content.put("feedback", "Your preferences have been saved. They will be applied when you restart the application.");
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}
	
	private boolean isIP(String ip){
		try {
			if(!ip.startsWith("tcp://"))
				return false;
			String adress = ip.substring(6);
			adress = adress.replaceAll("[\n\r]", "");
			if(adress.split("\\.").length != 4)
				return false;
			for(String i : adress.split("\\.")) {
				if(Integer.parseInt(i) >= 255)
					return false;
		}
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
