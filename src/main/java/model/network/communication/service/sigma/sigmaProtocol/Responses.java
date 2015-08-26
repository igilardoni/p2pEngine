package model.network.communication.service.sigma.sigmaProtocol;

import java.math.BigInteger;

import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;
import util.secure.AsymKeysImpl;

/**
 * It's response to need to send in the protocol
 * it's abstract, for the different response in the protocol
 * @author sarah
 * TODO extends AbstractAdvertisement ?
 */
public abstract class Responses extends AbstractAdvertisement {
	
	private Masks masks;
	private BigInteger challenge;
	private BigInteger response;
	
	public Responses(Masks mask, BigInteger challenge, BigInteger response)
	{
		this.setMasks(mask);
		this.setChallenge(challenge);
		this.setResponse(response);
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
	protected BigInteger getChallenge() {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void putValues() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean handleElement(Element e) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
