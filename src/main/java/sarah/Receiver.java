package sarah;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;


/*
 * TODO NEED DESCRIPTION
 */
public class Receiver {

	public Boolean Verifies (Responses response, Keys tKeys, ResEncrypt res)
	{
		return response.Verifies(tKeys, res);
	}

	public Boolean Verifies(HashMap <Responses,Keys> rK,ResEncrypt resEncrypt, Responses ... responses)
	{
		And and = new And(this, rK);
		return and.verifies(resEncrypt, responses);
	}

	protected Boolean VerifiesChallenge(BigInteger challenge,Masks mask, byte[] message)
	{
		BigInteger test;
		byte[] buffer, resume;
		MessageDigest hash_function = null;

		String tmp = message.toString().concat(mask.getA().toString());

		buffer = tmp.getBytes();

		try {
			hash_function = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resume = hash_function.digest(buffer);
		test = new BigInteger(resume);

		return (challenge.equals(test));
	}

	protected Boolean VerifiesChallenges(byte[] message, BigInteger a, ArrayList<BigInteger> challenge)
	{
		byte[] buffer, resume;
		MessageDigest hash_function = null;

		String tmp = message.toString().concat(a.toString());

		buffer = tmp.getBytes();

		try {
			hash_function = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resume = hash_function.digest(buffer);
		BigInteger test = new BigInteger(resume);

		BigInteger challenges = BigInteger.ZERO;
		for(BigInteger c : challenge)
			challenges = challenges.xor(c);

		return (challenges.equals(test));
	}



}
