/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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
