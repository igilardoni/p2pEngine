package sarah;
import java.util.HashMap;

/**
 * 
 * FOR TEST ???? TODO NEED COMMENTS !
 *
 */
public class Main {

	public static void main(String[] args) {
		Sender Bob = new Sender();
		Receiver Alice = new Receiver();
		Trent Trent = new Trent();
		
		String message = "Bonjour";
		byte[] buffer = message.getBytes();
		
		ResEncrypt resEncrypt = Bob.Encryption(buffer, Trent.getPublicKeyAs(), Trent.getPublicKeys());
		ResponsesSchnorr resSchnorr = Bob.SendResponseSchnorr(resEncrypt.getM());
		ResponsesCCE resCCE = Bob.SendResponseCCE(resEncrypt.getM(), Trent.getPublicKeys());
		
		ResponsesCCD resT = Trent.SendResponse(resEncrypt);
		
		/*
		if (!Alice.Verifies(resSchnorr, Bob.getPublicKeys(), resEncrypt))
			System.out.println("Bob is a liar, it's not Bob");
		else if (!Alice.Verifies(resCCE, Trent.getPublicKeys(), resEncrypt))
			System.out.println("Bob is a liar, he doesn't send to Trent");
		else 
			System.out.println("OK I'm conviced by Bob");
		
		if (!Alice.Verifies(resT, Trent.getPublicKeys(), resEncrypt))
			System.out.println("Trent is a liar");
		else 
			System.out.println("OK, I'm conviced by Trent");
		*/
		
		HashMap <Responses,Keys> rK  = new HashMap <Responses,Keys>();
		rK.put(resSchnorr, Bob.getPublicKeys());
		rK.put(resCCE, Trent.getPublicKeys());
		
		boolean happyAlice = Alice.Verifies(rK, resEncrypt, resSchnorr,resCCE);
		if(happyAlice)
			System.out.println("Alice is happy !");
		else
			System.out.println("Alice is sad !");
	}
}
