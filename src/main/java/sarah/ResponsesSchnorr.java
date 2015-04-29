package sarah;
import java.math.BigInteger;


public class ResponsesSchnorr extends Responses{

	public ResponsesSchnorr(Masks mask, BigInteger challenge,
			BigInteger response) {
		super(mask, challenge, response);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean Verifies(Keys tKeys, ResEncrypt res) {
		return (tKeys.getG().modPow(getResponse(), tKeys.getP()).equals(((tKeys.getPublicKey().modPow(getChallenge(), tKeys.getP())).multiply(getMasks().getA())).mod(tKeys.getP())));
	}
	

}
