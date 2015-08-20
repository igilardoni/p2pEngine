package util.secure.sigmaProtocol;
import java.util.HashMap;

import util.secure.AsymKeysImpl;

/**
 * This class is for the composability of reponses. This is clause And.
 * @author sarah
 *
 */

public class And {
	
	public Receiver receiver;
	public ResEncrypt resEncrypt;
	public HashMap <Responses,AsymKeysImpl> rK  = new HashMap <Responses,AsymKeysImpl>();
	public Responses[] responses; 
	
	/**
	 * Constructor
	 * @param receiver 
	 * @param rK (HashMap for each response associate with Keys)
	 * @param resEncrypt 
	 * @param responses (all responses to need verify)
	 */
	
	public And (Receiver receiver, HashMap <Responses,AsymKeysImpl> rK,  ResEncrypt resEncrypt, Responses ... responses)
	{
		this.receiver = receiver;
		this.rK  = rK;
		this.resEncrypt= resEncrypt;
		this.responses = responses;
	}
	
	/**
	 * Verify if set of responses is true or not for the receiver 
	 * @param or 
	 * if "or" the receiver doesn't verify if challenge it's good
	 * @return boolean 
	 */
	public Boolean Verifies(Boolean or)
	{
		for(Responses res : responses)
		{
			if (!or)
			{
				if (!receiver.VerifiesChallenge(res.getChallenge(), res.getMasks(), resEncrypt.getM()))
				{
					System.out.println("the challenge is fabricated");
					return false;
				}
			}
			
			if (!receiver.Verifies(res, rK.get(res), resEncrypt))
			{
				System.out.println("il y a un probleme");
				return false;
			}
		}
		System.out.println("tout est ok");
		return true;
		
	}
}
