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
package model.network.communication.service.sigma.sigmaProtocol;

import java.math.BigInteger;

import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;
import util.secure.AsymKeysImpl;

/**
 * It's response to need to send in the protocol
 * it's abstract, for the different response in the protocol
 * @author Sarah Boukris
 * @author Julien Prudhomme
 */
public abstract class Responses extends AbstractAdvertisement {
	
	private Masks masks;
	private BigInteger challenge;
	private BigInteger response;
	
	public Responses(Masks mask, BigInteger challenge, BigInteger response)
	{
		super();
		this.setMasks(mask);
		this.setChallenge(challenge);
		this.setResponse(response);
	}
	
	public Responses(String xml) {
		super(xml);
	}
	
	public Responses(Element root) {
		super(root);
	}
	
	@SuppressWarnings("rawtypes")
	public Responses(net.jxta.document.Element root) {
		super(root);
	}
	
	public Masks getMasks() {
		return masks;
	}
	public void setMasks(Masks masks) {
		this.masks = masks;
	}
	public BigInteger getResponse() {
		return response;
	}
	public void setResponse(BigInteger response) {
		this.response = response;
	}
	public BigInteger getChallenge() {
		return challenge;
	}
	public void setChallenge(BigInteger challenge) {
		this.challenge = challenge;
	}
	
	/**
	 * Verify the response according to the type of response
	 * @param Keys
	 * @param res
	 * @return
	 */
	public abstract Boolean Verifies(AsymKeysImpl Keys, ResEncrypt res);


	@Override
	protected void setKeys() {
		addKey("masks", false, false);
		addKey("challenge", false, false);
		addKey("response", false, false);
	}

	@Override
	protected void putValues() {
		addValue("masks", masks.toString());
		addValue("challenge", challenge.toString(16));
		addValue("response", response.toString(16));
	}

	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "masks": masks = new Masks(e.getValue()); return true;
		case "challenge": challenge = new BigInteger(e.getValue(), 16); return true;
		case "response": response = new BigInteger(e.getValue(), 16); return true;
		default: return false;
		}
	}
	
}
