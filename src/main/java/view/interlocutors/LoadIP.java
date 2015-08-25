package view.interlocutors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.Application;
import model.data.RendezVousIp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.Printer;
import util.VARIABLES;

public class LoadIP extends AbstractInterlocutor {

	public LoadIP() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			try {
				if(new File(VARIABLES.BootstrapFilePath).exists()){
					FileReader xmlFile = new FileReader(VARIABLES.BootstrapFilePath);
					BufferedReader br = new BufferedReader(xmlFile);
				    StringBuilder sb = new StringBuilder();
				    String line = br.readLine();
		
				    while (line != null) {
				        sb.append(line);
				        sb.append(System.lineSeparator());
				        line = br.readLine();
				    }
				    String xml = sb.toString();
				    br.close();
					RendezVousIp rdv = new RendezVousIp(xml);
					for(String ip : rdv.getIps()){
						JSONObject data = new JSONObject();
						JSONObject content = new JSONObject();
						data.put("query", "ipLoaded");
						content.put("ip", ip);
						data.put("content", content);
						com.sendText(data.toString());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			data.put("query", "ipLoaded");
			content.put("ip", Application.getInstance().getNetwork().getBootStrapIp());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}
}
