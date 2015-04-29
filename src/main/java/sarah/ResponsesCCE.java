package sarah;
import java.math.BigInteger;


public class ResponsesCCE extends Responses {

	public ResponsesCCE(Masks mask, BigInteger challenge, BigInteger response) {
		super(mask, challenge, response);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean Verifies(Keys tKeys, ResEncrypt res) {
		
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

}
