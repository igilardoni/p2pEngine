package model.user;

import java.math.BigInteger;

import util.secure.AsymKeysImpl;

/**
 * @deprecated
 * @author michael
 *
 */
public class TwinKey {
	private BigInteger p;
	private BigInteger g;
	private BigInteger publicKey;
	private BigInteger privateKey;
	
	public TwinKey(){}
	
	public TwinKey(boolean pg){
		AsymKeysImpl gen = new AsymKeysImpl(pg);
		setP(gen.getP());
		setG(gen.getG());
		setPublicKey(gen.getPublicKey());
		setPrivateKey(gen.getPrivateKey());
	}
	
	private void setPublicKey(BigInteger publicKey){
		this.publicKey = publicKey;
	}
	private void setPrivateKey(BigInteger privateKey){
		this.privateKey = privateKey;
	}
	private void setP(BigInteger p){
		this.p = p;
	}
	private void setG(BigInteger g){
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
