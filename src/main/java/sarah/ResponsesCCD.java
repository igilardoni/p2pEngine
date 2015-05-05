package sarah;
import java.math.BigInteger;


/*
 * TODO NEED DESCRIPTION
 */
public class ResponsesCCD extends Responses {

	public ResponsesCCD(Masks mask, BigInteger challenge, BigInteger response) {
		super(mask, challenge, response);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean Verifies( Keys tKeys, ResEncrypt res) {
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
