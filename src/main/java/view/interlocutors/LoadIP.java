/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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
