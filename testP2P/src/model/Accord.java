package model;

import java.io.Serializable;

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
	
}
