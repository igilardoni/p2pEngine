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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.Application;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.Printer;
import controller.ManagerBridge;

public class SendBootstrap_OLD extends AbstractInterlocutor {

	public SendBootstrap_OLD() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			boolean error = false;
			JSONObject c = getJSON(content);
			String to = c.getString("emailReceiver");
			String from = c.getString("emailSender");
			String pwd = c.getString("passwordSender");
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			content.put("feedback", "");
			if(to.isEmpty() || to.equals("")) {
				content.put("feedback", "Receiver's email is empty !");
				Printer.printError(this, "", "to empty");
				error = true;
			}
			if(!to.contains("@") && !to.isEmpty()){
				if(content.getString("feedback").isEmpty())
					content.put("feedback", "Receiver's adress is invalid !");
				else
					content.put("feedback", content.getString("feedback")+"<br/>Receiver's adress is invalid !");
				Printer.printError(this, "", "adress not valide !");
				error = true;
			}
			if(from.isEmpty() || from.equals("")) {
				if(content.getString("feedback").isEmpty())
					content.put("feedback", "Your email is empty !");
				else
					content.put("feedback", content.getString("feedback")+"<br/>Your email is empty !");
				Printer.printError(this, "", "from empty");
				error = true;
			}
			if(!from.contains("@") && !from.isEmpty()){
				if(content.getString("feedback").isEmpty())
					content.put("feedback", "Your adress is invalid !");
				else
					content.put("feedback", content.getString("feedback")+"<br/>Your adress is invalid !");
				Printer.printError(this, "", "adress not valide !");
				error = true;
			}
			if(pwd.isEmpty()) {
				if(content.getString("feedback").isEmpty())
					content.put("feedback", "Your password is empty !");
				else
					content.put("feedback", content.getString("feedback")+"<br/>Your password is empty !");
				Printer.printError(this, "", "from empty");
				error = true;
			}
			if(error){
				data.put("query", "bootstrapNotSent");
				data.put("content", content);
				com.sendText(data.toString());
				return;
			}
			
			 // TODO Maybe a class to send mail
			String smtpHost = "smtp."+from.split("@")[1];
			String username = from;
			 
			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			 
			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);
			 
			MimeMessage message = new MimeMessage(session);   
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			message.setSubject("SXP Invitation from "+
			ManagerBridge.getCurrentUser().getName()+" "+
			ManagerBridge.getCurrentUser().getFirstName());
			message.setText(Application.getInstance().getNetwork().getBootStrapIp());
			
			/* 				Example to send attachment
			 * TODO Class to create attachment !!!!!!
			 * messageBodyPart = new MimeBodyPart();
			 * DataSource source = new FileDataSource("image.gif");
			 * messageBodyPart.setDataHandler(new DataHandler(source));
			 * messageBodyPart.setFileName("image.gif");
			 * multipart.addBodyPart(messageBodyPart); 
			 */
			
			Transport tr = session.getTransport("smtp");
			tr.connect(smtpHost, username, pwd);
			message.saveChanges();
			
			tr.sendMessage(message,message.getAllRecipients());
			tr.close();
	        
			data.put("query", "bootstrapSent");
			content.put("feedback", "Message sent to "+to);
			data.put("content", content);
			com.sendText(data.toString());
		} catch (AddressException e) {
			e.printStackTrace();
			try {
				JSONObject data = new JSONObject();
				JSONObject content = new JSONObject();
				data.put("query", "bootstrapNotSent");
				content.put("feedback", e.toString());
				data.put("content", content);
				com.sendText(data.toString());
				Printer.printError(this, "", e.toString());
			} catch (JSONException e1) {
				Printer.printError(this, "", e1.toString());
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			try {
				JSONObject data = new JSONObject();
				JSONObject content = new JSONObject();
				data.put("query", "bootstrapNotSent");
				content.put("feedback", e.toString());
				data.put("content", content);
				com.sendText(data.toString());
				Printer.printError(this, "", e.toString());
			} catch (JSONException e1) {
				Printer.printError(this, "", e1.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
