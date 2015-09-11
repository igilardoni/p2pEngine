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
package util.secure;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import util.Hexa;

public class ElGamalTest {
	private static AsymKeysImpl keysBob;
	private static AsymKeysImpl keysAliceKnow;
	private static String msgAliceToBob, msgBobToAlice;
	private static ElGamal elGamalAlice, elGamalBob;
	
	@BeforeClass
	public static void init(){
		keysBob = new AsymKeysImpl("");
		keysAliceKnow = new AsymKeysImpl(keysBob.getP(), keysBob.getG(), keysBob.getPublicKey());
		msgAliceToBob = "Hello dear Bob !";
		msgBobToAlice = "Hello dear Alice !";
		elGamalAlice = new ElGamal(keysAliceKnow);
		elGamalBob = new ElGamal(keysBob);
	}
	
	@Test
	public void cryptDecrypt() {
		// Alice encrypts :
		byte[] encrypted = elGamalAlice.encryptWithPublicKey(msgAliceToBob.getBytes());
		String encryptedAlice = Hexa.bytesToHex(encrypted);
		
		// Bob decrypts :
		byte[] decrypted = elGamalBob.decryptWithPrivateKey(Hexa.hexToBytes(encryptedAlice));
		String decryptedBob = Hexa.bytesToString(decrypted);
		
		assertEquals(msgAliceToBob, decryptedBob);
	}
	
	@Test
	public void signVerify(){
		
		ElGamalSign sign = elGamalBob.getMessageSignature(msgBobToAlice.getBytes());
		boolean signatureVerif = elGamalAlice.verifySignature(msgBobToAlice.getBytes(), sign);
		assertTrue(signatureVerif);
	}
}
