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
			String to = c.getString("emailReceiver");
			String from = c.getString("emailSender");
			String pwd = c.getString("passwordSender");
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			if(to.isEmpty() || to.equals("")){
				data.put("query", "bootstrapNotSent");
				content.put("error", "email receiver empty !");
				content.put("fieldError", "emailReceiver");
				data.put("content", content);
				com.sendText(data.toString());
				Printer.printError(this, "", "email receiver empty");
				return;
			}
			if(from.isEmpty() || from.equals("")){
				data.put("query", "bootstrapNotSent");
				content.put("error", "yout email is empty !");
				content.put("fieldError", "emailSender");
				data.put("content", content);
				com.sendText(data.toString());
				Printer.printError(this, "", "from empty");
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
			session.setDebug(true);
			 
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
	        
			data.put("query", "boostrapSent");
			content.put("message", "Message sent to "+to);
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
