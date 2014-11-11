package model;

import java.io.Serializable;

import model.pdf.ContratPdfGenerator;
import model.pdf.ObjetPdfModel;
import model.pdf.UserPdfModel;

public class Accord implements Serializable{

	private static final long serialVersionUID = -9198766713234619409L;
	private String from, to;
	private String messageFrom, messageTo;
	private String annonce;
	private boolean accepted;
	private boolean rated;
	
	public Accord(String from, String to, String messageFrom, String annonce) {
		this.from = from;
		this.to = to;
		this.messageFrom = messageFrom;
		this.annonce = annonce;
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	public void setAccepted() {
		accepted = true;
	}
	
	public boolean isRated() {
		return rated;
	}
	
	public void setRated() {
		rated = true;
	}
	
	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	public String getAnnonce() {
		return annonce;
	}
	
	public String getMessageFrom() {
		return messageFrom;
	}
	
	public String getMessageTo() {
		return messageTo;
	}

	
	public ObjetPdfModel createModelObjet(Objet obj){
		return new ObjetPdfModel(obj);
	}
	public UserPdfModel createModelUser(User user){
		return new UserPdfModel(user);
	}
	
	public void createContrat(Objet obj1, Objet obj2, User user1, User user2) {
		ObjetPdfModel modelObj1 = createModelObjet(obj1);
		UserPdfModel modelUser1 = createModelUser(user1);
		UserPdfModel modelUser2 = createModelUser(user2);
		ObjetPdfModel modelObj2 = null;
		if(obj2 !=  null){
			modelObj2 = createModelObjet(obj2);
			new ContratPdfGenerator(modelObj1, modelObj2, modelUser1, modelUser2);
		}
		new ContratPdfGenerator(modelObj1, modelUser1, modelUser2);
		
	}
	
}
