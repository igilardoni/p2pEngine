package sarah;
import java.util.HashMap;

/*
 * TODO Need description
 */
public class And {
	public Receiver receiver;
	public HashMap <Responses,Keys> rK  = new HashMap <Responses,Keys>();

	/**
	 * Constructor
	 * TODO Need description
	 * @param receiver
	 * @param rK
	 */
	public And(Receiver receiver, HashMap <Responses,Keys> rK){
		this.receiver = receiver;
		this.rK  = rK;
	}
	
	/**
	 * TODO Need description
	 * @param resEncrypt
	 * @param responses
	 * @return
	 */
	public Boolean verifies(ResEncrypt resEncrypt, Responses ...responses){
		for(Responses res : responses){
			if (!receiver.VerifiesChallenge(res.getChallenge(), res.getMasks(), resEncrypt.getM())){
				System.out.println("the challenge is fabricated");
				return false;
			}
			if (!receiver.Verifies(res, rK.get(res), resEncrypt)){
				System.out.println("il y a un probleme");
				return false;
			}
		}
		return true;
	}
}
