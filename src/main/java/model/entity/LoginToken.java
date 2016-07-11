package model.entity;

import javax.persistence.Entity;

@Entity
public class LoginToken {
	private String token;
	private long userid;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long l) {
		this.userid = l;
	}
}
