package model.communications;

import java.io.Serializable;

public class MessageData implements Serializable{

	private static final long serialVersionUID = -5218533412702632722L;
	private String sender;
	private String to;
	private String content;
	private long date;
	
	public MessageData(String sender, String to, String content, long date) {
		this.content = content;
		this.date = date;
		this.sender = sender;
		this.to = to;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getTo() {
		return to;
	}
	
	public long getDate() {
		return date;
	}
	
	public String getSender() {
		return sender;
	}
}