package model.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import crypt.api.key.AsymKey;

@Entity
public class Key implements AsymKey<BigInteger>{
	
	@NotNull
	@XmlElement(name="privateKey")
	private BigInteger privateKey;
	
	@NotNull
	@XmlElement(name="publicKey")
	private BigInteger publicKey;
	
	private BigInteger p;
	private BigInteger g;
	
	@Override
	public BigInteger getPublicKey() {
		return publicKey;
	}
	@Override
	public BigInteger getPrivateKey() {
		return privateKey;
	}
	@Override
	public BigInteger getParam(String param) {
		switch(param) {
		case "p": return p;
		case "g": return g;
		default: throw new RuntimeException("param " + param + "undefined");
		}
	}
	@Override
	public void setPublicKey(BigInteger pbk) {
		publicKey = pbk;
	}
	@Override
	public void setPrivateKey(BigInteger pk) {
		privateKey = pk;
	}
	
	
	
}
