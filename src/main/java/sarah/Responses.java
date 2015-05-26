package sarah;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * It's response to need to send in the protocol
 * it's abstract, for the different response in the protocol
 * @author sarah
 *
 */
public abstract class Responses {
	
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
	public abstract Boolean Verifies(Keys Keys, ResEncrypt res);
	
}
