package sarah;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * TODO NEED DESCRIPTION
 */
public class Or {

	public Receiver receiver;

	public HashMap <Responses,Keys> rK  = new HashMap <Responses,Keys>();
	public ArrayList <BigInteger> challenges = new ArrayList <BigInteger>(); 
	private BigInteger a;
	private byte[] M; 

	public Or (Receiver receiver, HashMap <Responses,Keys> rK, BigInteger a, byte[] M)
	{
		this.receiver = receiver;
		this.rK  = rK;
		this.setA(a);
		this.setM(M);
	}

	public Boolean Verifies(ResEncrypt resEncrypt, Responses ...responses)
	{
		for(Responses res : responses)
		{
			challenges.add(res.getChallenge());
			if (!receiver.Verifies(res, rK.get(res), resEncrypt))
			{
				System.out.println("il y a un probleme");
				return false;
			}
		}

		if (!receiver.VerifiesChallenges(getM(), getA(), challenges))
		{
			System.out.println("probleme dans les challenges");
		}
		return true;

	}

	public BigInteger getA() {
		return a;
	}

	public void setA(BigInteger a) {
		this.a = a;
	}

	public byte[] getM() {
		return M;
	}

	public void setM(byte[] m) {
		M = m;
	}
}
