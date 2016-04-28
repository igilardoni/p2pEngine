package crypt.impl;

import org.junit.Test;
import static org.junit.Assert.*;
import crypt.factories.ElGamalAsymKeyFactory;
import crypt.impl.key.ElGamalAsymKey;
import crypt.impl.signatures.ElGamalSignature;
import crypt.impl.signatures.ElGamalSigner;

public class ElGamalSignerTest {
	private static final String message1 = "LoremIpsum";
	private static final String message2 = "Lorem Ipsum";
	
	@Test
	public void test() {
		ElGamalAsymKey key1 = ElGamalAsymKeyFactory.create(false);
		ElGamalAsymKey key2 = ElGamalAsymKeyFactory.create(false);
		
		ElGamalSigner signer = new ElGamalSigner();
		signer.setKey(key1);
		ElGamalSignature sign = signer.sign(message1.getBytes());
		assertTrue(message1, signer.verify(message1.getBytes(), sign));
		assertFalse(message1, signer.verify(message2.getBytes(), sign));
		signer.setKey(key2);
		assertFalse(message2, signer.verify(message1.getBytes(), sign));
	}
}
