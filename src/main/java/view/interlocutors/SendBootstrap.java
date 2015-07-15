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

public class SendBootstrap extends AbstractInterlocutor {

	public SendBootstrap() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			JSONObject c = getJSON(content);
			String to = c.getString("email");
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			if(to.isEmpty()){
				data.put("query", "bootstrapNotSent");
				content.put("error", "email receiver empty !");
				data.put("content", content);
				com.sendText(data.toString());
				Printer.printError(this, "", "email receiver empty");
				return;
			}
			
			// Try to send email
			String from = ManagerBridge.getCurrentUser().getEmail();
			if(from == null || from.isEmpty()){
				data.put("query", "bootstrapNotSent");
				content.put("error", "yout email is empty !");
				data.put("content", content);
				com.sendText(data.toString());
				Printer.printError(this, "", "from empty");
				return;
			}
			String host = "localhost";
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", host);
			Session session = Session.getDefaultInstance(properties);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			message.setSubject("SXP Invitation from "+
					ManagerBridge.getCurrentUser().getName()+" "+
					ManagerBridge.getCurrentUser().getFirstName());
	        message.setText(Application.getInstance().getNetwork().getBootStrapIp());
	        
	        Transport.send(message);
			// End of try
	        
			data.put("query", "boostrapSent");
			content.put("ip", Application.getInstance().getNetwork().getBootStrapIp());
			data.put("content", content);
			com.sendText(data.toString());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
			try {
				JSONObject data = new JSONObject();
				JSONObject content = new JSONObject();
				data.put("query", "bootstrapNotSent");
				content.put("error", e.getCause());
				data.put("content", content);
				com.sendText(data.toString());
				Printer.printError(this, "", "from empty");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
