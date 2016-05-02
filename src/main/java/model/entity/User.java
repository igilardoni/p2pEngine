package model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class User {
	
	@Id
	@XmlElement(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@XmlElement(name="nick")
	@NotNull
	@Size(min = 3, max = 64)
	private String nick;
	
	@XmlElement(name="salt")
	@NotNull
	private String salt;
	
	@XmlElement(name="passwordHash")
	@NotNull
	private String passwordHash;
	
	@Temporal(TemporalType.TIME)
	@NotNull
	@XmlElement(name="createdAt")
	private Date createdAt;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
}
