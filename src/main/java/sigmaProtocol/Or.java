package sigmaProtocol;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is for the composability. This is clause Or.
 * @author sarah
 *
 */
public class Or {

	public Receiver receiver;
	
	public And[] ands;
	public ArrayList <BigInteger> challenges = new ArrayList <BigInteger>(); 
	private BigInteger a;
	
	/**
	 * Constructor
	 * @param receiver
	 * @param a (a mask)
	 * @param ands (set of clause and to need to verify)
	 */
	public Or (Receiver receiver, BigInteger a, And ... ands)
	{
		this.receiver = receiver;
		this.ands  = ands;
		this.setA(a);
	}
	
	/**
	 * Verifies if clauses in the Or is true or not for the receiver
	 * @param resEncrypt
	 * @return Boolean
	 */
	public Boolean Verifies(ResEncrypt resEncrypt)
	{
		for(And and : ands)
		{
			if (!receiver.Verifies(and, true))
			{
				System.out.println("il y a un probleme");
				return false;
			}
			
			for (Responses res : and.responses)
				challenges.add(res.getChallenge());
		}
		
		if (!receiver.VerifiesChallenges(resEncrypt.getM(), getA(), challenges))
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
}
