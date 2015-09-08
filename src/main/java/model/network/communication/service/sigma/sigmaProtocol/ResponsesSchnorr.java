package model.network.communication.service.sigma.sigmaProtocol;
import java.math.BigInteger;

import util.secure.AsymKeysImpl;

/**
 * The Schnorr response
 * @author sarah
 *
 */
public class ResponsesSchnorr extends Responses{

	/**
	 * Constructo
	 * @param mask
	 * @param challenge
	 * @param response
	 */
	public ResponsesSchnorr(Masks mask, BigInteger challenge,
			BigInteger response) {
		super(mask, challenge, response);
	}
	
	public ResponsesSchnorr(String xml) {
		super(xml);
	}

	@Override
	/**
	 * Extends Responses
	 * Verify if the Schnorr response is good or not 
	 */
	public Boolean Verifies(AsymKeysImpl tKeys, ResEncrypt res) {
		return (tKeys.getG().modPow(getResponse(), tKeys.getP()).equals(((tKeys.getPublicKey().modPow(getChallenge(), tKeys.getP())).multiply(getMasks().getA())).mod(tKeys.getP())));
	}

	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}

	@Override
	protected String getAdvertisementName() {
		return getClass().getName();
	}
	

}
