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
		keysBob = new AsymKeysImpl(false);
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
		byte[] decrypted = elGamalBob.descryptWithPrivateKey(Hexa.hexToBytes(encryptedAlice));
		String decryptedBob = Hexa.bytesToString(decrypted);
		
		assertEquals(msgAliceToBob, decryptedBob);
	}
	
	@Test
	public void signVerify(){
		// Bob signs :
		try {
			elGamalBob.Signs(msgBobToAlice.getBytes());
		} catch (Exception e) {
			fail(e.toString());
		}
		
		// Alice verifies
		elGamalAlice.setR(elGamalBob.getR());
		elGamalAlice.setS(elGamalBob.getS());
		try {
			boolean signatureVerif = elGamalAlice.VerifieSignature(msgBobToAlice.getBytes());
			assertEquals(signatureVerif, true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}
