/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package model.network.communication.service.sigma;

import java.math.BigInteger;
import java.util.HashMap;

import model.network.communication.service.sigma.sigmaProtocol.And;
import model.network.communication.service.sigma.sigmaProtocol.Masks;
import model.network.communication.service.sigma.sigmaProtocol.Receiver;
import model.network.communication.service.sigma.sigmaProtocol.ResEncrypt;
import model.network.communication.service.sigma.sigmaProtocol.Responses;
import model.network.communication.service.sigma.sigmaProtocol.ResponsesCCE;
import model.network.communication.service.sigma.sigmaProtocol.ResponsesSchnorr;
import model.network.communication.service.sigma.sigmaProtocol.Sender;
import model.network.communication.service.sigma.sigmaProtocol.Trent;
import model.network.communication.service.sigma.sigmaProtocol.Utils;
import util.secure.AsymKeysImpl;

public class Main {

	public static void main(String[] args)
	{
		AsymKeysImpl bobK, aliceK, trentK;
		String pass ="mariomario";
		bobK = new AsymKeysImpl(false, pass);
		bobK.decryptPrivateKey(pass);
		aliceK = new AsymKeysImpl(false, pass);
		aliceK.decryptPrivateKey(pass);
		trentK = new AsymKeysImpl(false, pass);
		trentK.decryptPrivateKey(pass);
		Sender Bob = new Sender(bobK);
		Sender Alice = new Sender(aliceK);
		
		Receiver AliceR = new Receiver();
		
		Trent Trent = new Trent(trentK);
		
		String message = "Bonjour toi !";
		byte[] buffer = message.getBytes();
		
		// Bob encrypte le message pour Trent
		ResEncrypt resEncrypt = Bob.Encryption(buffer, Trent.getKey());
		
		// Bob cree le masque Schnorr et le Challenge 
		Masks mask = Bob.SendMasksSchnorr();
		BigInteger a = mask.getA();
		BigInteger challenge = Bob.SendChallenge(mask, resEncrypt.getM());
		
		// Bob fabrique les reponses pour charlie
		ResponsesSchnorr resSchnorrF = Bob.SendResponseSchnorrFabric(Alice.getKeys());
		ResponsesCCE resCCEF = Bob.SendResponseCCEFabric(resEncrypt, Trent.getKey());
		
		// trouve le challenge de BOB
		BigInteger c0 = challenge.xor(resCCEF.getChallenge()).xor(resSchnorrF.getChallenge());
		
		BigInteger c = Utils.rand(160, Bob.getKeys().getP());
		BigInteger c1 = c0.xor(c);
		
		ResponsesCCE resCCE = Bob.SendResponseCCE(resEncrypt.getM(), Trent.getKey(),c1);
		

		
		if(AliceR.Verifies(resCCE, trentK, resEncrypt)) {
			System.out.println("ca aussi ca marche ;)");
		}
	
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
