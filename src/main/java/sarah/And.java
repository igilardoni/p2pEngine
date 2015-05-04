package sarah;
import java.util.HashMap;


public class And {
	
	public Receiver receiver;
	
	public HashMap <Responses,Keys> rK  = new HashMap <Responses,Keys>();
	
	public And (Receiver receiver, HashMap <Responses,Keys> rK)
	{
		this.receiver = receiver;
		this.rK  = rK;
	}
	
	public Boolean Verifies(ResEncrypt resEncrypt, Responses ...responses)
	{
		for(Responses res : responses)
		{
			if (!receiver.VerifiesChallenge(res.getChallenge(), res.getMasks(), resEncrypt.getM()))
			{
				System.out.println("the challenge is fabricated");
				return false;
			}
			if (!receiver.Verifies(res, rK.get(res), resEncrypt))
			{
				System.out.println("il y a un probleme");
				return false;
			}
		}
		return true;
		
	}
}
