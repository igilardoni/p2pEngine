package sarah;

import java.math.BigInteger;
import java.util.HashMap;


public class Main {

	public static void main(String[] args)
	{
		Sender Bob = new Sender();
		Sender Charlie = new Sender();
		
		Receiver Alice = new Receiver();
		
		Trent Trent = new Trent();
		
		String message = "Bonjour";
		byte[] buffer = message.getBytes();
		
		ResEncrypt resEncrypt = Bob.Encryption(buffer, Trent.getPublicKeyAs(), Trent.getPublicKeys());
		
		Masks mask = Bob.SendMasksSchnorr();
		BigInteger a = mask.getA();
		BigInteger challenge = Bob.SendChallenge(mask, resEncrypt.getM());
		
		// fabrique les reponses pour charlie
		ResponsesSchnorr resSchnorrF = Bob.SendResponseSchnorrFabric(Charlie.getPublicKeys());
		ResponsesCCE resCCEF = Bob.SendResponseCCEFabric(resEncrypt, Trent.getPublicKeys());
		
		// trouve le challenge de BOB
		BigInteger c0 = challenge.xor(resCCEF.getChallenge()).xor(resSchnorrF.getChallenge());
		
		BigInteger c = Utils.rand(160, Bob.getPublicKeys().getP());
		BigInteger c1 = c0.xor(c);
		
		ResponsesSchnorr resSchnorr = Bob.SendResponseSchnorr(resEncrypt.getM(),c);
		ResponsesCCE resCCE = Bob.SendResponseCCE(resEncrypt.getM(), Trent.getPublicKeys(),c1);
		
		// Or
		HashMap <Responses,Keys> rK1  = new HashMap <Responses,Keys>();
		rK1.put(resSchnorr, Bob.getPublicKeys());
		rK1.put(resCCE, Trent.getPublicKeys());
		
		HashMap <Responses,Keys> rK  = new HashMap <Responses,Keys>();
		rK.put(resSchnorrF, Charlie.getPublicKeys());
		rK.put(resCCEF, Trent.getPublicKeys());
		
		And andC = new And(Alice,rK,resEncrypt,resSchnorrF,resCCEF);
		And andB = new And(Alice,rK1,resEncrypt,resSchnorr,resCCE);
		
		Alice.Verifies(a, resEncrypt, andC, andB);
	
		/*ResponsesSchnorr resSchnorr = Bob.SendResponseSchnorr(resEncrypt.getM());
		ResponsesCCE resCCE = Bob.SendResponseCCE(resEncrypt.getM(), Trent.getPublicKeys());
		
		if (!Alice.Verifies(resSchnorr, Bob.getPublicKeys(), resEncrypt))
			System.out.println("Bob is a liar, it's not Bob");
		
		else if (!Alice.Verifies(resCCE, Trent.getPublicKeys(), resEncrypt))
			System.out.println("Bob is a liar, he doesn't send to Trent");
		
		else 
			System.out.println("OK I'm conviced by Bob");*/
		
		/*if (!Alice.Verifies(resT, Trent.getPublicKeys(), resEncrypt))
			System.out.println("Trent is a liar");
		
		else 
		{
			System.out.println("OK, I'm conviced by Trent");
		}*/
		/*HashMap <Responses,Keys> rK  = new HashMap <Responses,Keys>();
		rK.put(resSchnorr, Bob.getPublicKeys());
		rK.put(resCCE, Trent.getPublicKeys());
		
		Alice.Verifies(false, rK, resEncrypt, resSchnorr,resCCE);*/
	}
}
