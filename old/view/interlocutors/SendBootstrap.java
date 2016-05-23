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

import java.io.File;
import java.io.IOException;

import model.Application;
import model.data.RendezVousIp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import util.VARIABLES;
import controller.ManagerBridge;

public class SendBootstrap extends AbstractInterlocutor {

	public SendBootstrap() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			/*JSONObject c = getJSON(content);
			String emailReceiver = c.getString("emailReceiver");
			String emailSender = c.getString("emailSender");*/
			
			String subject = "Invitation to Secure eXchange Protocol from "
					+ ManagerBridge.getCurrentUser().getName() + " "
					+ ManagerBridge.getCurrentUser().getFirstName();
			String head = VARIABLES.EmailIntro;
			String body = Application.getInstance().getNetwork().getBootStrapIp() + "%0A";
			String foot = VARIABLES.EmailFoot;
			RendezVousIp rdvIp = new RendezVousIp();
			File file = new File("./bootstrap.xml");
			if(file.exists()) {
				SAXBuilder builder = new SAXBuilder();
				Document document = (Document) builder.build(file);
				Element root = document.getRootElement();
				rdvIp = new RendezVousIp(root);
				for (String ip : rdvIp.getIps()) {
					body += ip + "%0A";
				}
			}
			// Answer
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			data.put("query", "bootstrapSent");
			content.put("feedback", "");
			content.put("feedbackOk", true);
			content.put("subject", subject);
			content.put("text", head+body+foot);
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
