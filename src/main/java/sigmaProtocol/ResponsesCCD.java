package sigmaProtocol;
import java.math.BigInteger;

import util.secure.AsymKeysImpl;

/**
 * The CCD response
 * @author sarah
 *
 */
public class ResponsesCCD extends Responses {

	/**
	 * Constructor
	 * @param mask
	 * @param challenge
	 * @param response
	 */
	public ResponsesCCD(Masks mask, BigInteger challenge, BigInteger response) {
		super(mask, challenge, response);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	/**
	 * Extends Responses
	 * Verify if the CCD response is good or not 
	 */
	public Boolean Verifies( AsymKeysImpl tKeys, ResEncrypt res) {
		if (!tKeys.getG().modPow(getResponse(), tKeys.getP()).equals(((tKeys.getPublicKey().modPow(getChallenge(), tKeys.getP())).multiply(getMasks().getA())).mod(tKeys.getP())))
		{
			return false;
		}
		BigInteger M = new BigInteger (res.getM());
		if(!res.getU().modPow(getResponse(),tKeys.getP() ).equals(res.getV().divide(M).modPow(getChallenge(), tKeys.getP()).multiply(getMasks().getaBis()).mod(tKeys.getP())))
		{
			return false;
		}
		
		return true;
	}

}
