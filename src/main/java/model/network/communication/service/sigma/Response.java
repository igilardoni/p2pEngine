package model.network.communication.service.sigma;

import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;
import model.data.user.User;
import model.network.communication.service.sigma.sigmaProtocol.ResEncrypt;
import model.network.communication.service.sigma.sigmaProtocol.ResponsesCCE;

public class Response extends AbstractAdvertisement {

	public enum Type{
		SENDER_SEND_PROOF,
		SEND_SIGN
	}
	
	private ResponsesCCE resCCE;
	private User trent;
	private User sender;
	private String receiver;
	private ResEncrypt res;
	private Type type;
	
	
	public Response(String xml) {
		super(xml);
	}
	
	public Response(Type type) {
		super();
		this.type = type;
	}
	
	public ResponsesCCE getResponse() {
		return resCCE;
	}
	
	public void setResponse(ResponsesCCE r) {
		resCCE = r;
	}
	
	public User getTrent() {
		return trent;
	}
	
	public void setTrent(User u) {
		trent = u;
	}
	
	public User getSender() {
		return sender;
	}
	
	public void setSender(User u) {
		sender = u;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public void setReceiver(String s) {
		receiver = s;
	}
	
	public ResEncrypt getRes() {
		return res;
	}
	
	public void setRes(ResEncrypt res) {
		this.res = res;
	}
	
	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}

	@Override
	protected String getAdvertisementName() {
		return getClass().getName();
	}

	@Override
	protected void setKeys() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void putValues() {
		// TODO Auto-generated method stub
		
	}
	
	public Type getType() {
		return type;
	}

	@Override
	protected boolean handleElement(Element e) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
