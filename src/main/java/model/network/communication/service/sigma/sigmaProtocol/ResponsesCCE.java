package model.network.communication.service.sigma.sigmaProtocol;
import java.math.BigInteger;

import util.secure.AsymKeysImpl;

/**
 * The CCE response
 * @author sarah
 *
 */
public class ResponsesCCE extends Responses {

	/**
	 * Constructor
	 * @param mask
	 * @param challenge
	 * @param response
	 */
	public ResponsesCCE(Masks mask, BigInteger challenge, BigInteger response) {
		super(mask, challenge, response);
	}
	
	public ResponsesCCE(String xml) {
		super(xml);
	}

	@Override
	/**
	 * Extends Responses
	 * Verify if the CCE response is good or not 
	 */
	public Boolean Verifies(AsymKeysImpl tKeys, ResEncrypt res) {
		
		BigInteger gPowr = tKeys.getG().modPow(getResponse(),tKeys.getP());
		BigInteger uPowc = res.getU().modPow(getChallenge(), tKeys.getP());
		BigInteger uPowcMulta = uPowc.multiply(getMasks().getA()).mod(tKeys.getP());
		
		if(!gPowr.equals(uPowcMulta))
		{
			System.out.println("you are a liar");
			return false;
		}
		
		BigInteger M = new BigInteger (res.getM());
		BigInteger pubPowr = tKeys.getPublicKey().modPow(getResponse(), tKeys.getP());
		
		BigInteger vdivM = res.getV().divide(M);
		BigInteger vDivMPowc = vdivM.modPow(getChallenge(), tKeys.getP());
		BigInteger vDivMPowcMultaBis = (vDivMPowc.multiply((getMasks().getaBis()))).mod(tKeys.getP());
		
		if (!pubPowr.equals(vDivMPowcMultaBis))
		{
			System.out.println("you are a liar !");
			return false;
		}
		
		return true;
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
