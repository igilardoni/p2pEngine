package model.user;

import java.math.BigInteger;

import util.secure.KeyGenerator;

public class TwinKey {
	private BigInteger p;
	private BigInteger g;
	private BigInteger publicKey;
	private BigInteger privateKey;
	
	public TwinKey(){
		KeyGenerator gen = new KeyGenerator();
		setP(gen.getP());
		setG(gen.getG());
		setPublicKey(gen.getPublicKey());
		setPrivateKey(gen.getPrivateKey());
	}
	
	public TwinKey(boolean pg){
		KeyGenerator gen = new KeyGenerator(pg);
		setP(gen.getP());
		setG(gen.getG());
		setPublicKey(gen.getPublicKey());
		setPrivateKey(gen.getPrivateKey());
	}
	
	public void setPublicKey(BigInteger publicKey){
		this.publicKey = publicKey;
	}
	public void setPrivateKey(BigInteger privateKey){
		this.privateKey = privateKey;
	}
	public void setP(BigInteger p){
		this.p = p;
	}
	public void setG(BigInteger g){
		this.g = g;
	}
	
	public BigInteger getPublicKey(){
		return this.publicKey;
	}
	public BigInteger getPrivateKey(){
		return this.privateKey;
	}
	public BigInteger getP(){
		return this.p;
	}
	public BigInteger getG(){
		return this.g;
	}
}
