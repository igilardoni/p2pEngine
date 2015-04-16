package util.secure;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class KeyGeneratorTest {

	@Test(timeout=180000)
	public void GeneratorOfTwinKeys() {
		KeyGenerator gen;
		BigInteger verif;
		
		gen = new KeyGenerator(false);
		verif = gen.getG().modPow(gen.getPrivateKey(), gen.getP());
		assertEquals(gen.getPublicKey(), verif);
		
		gen = new KeyGenerator(true);
		verif = gen.getG().modPow(gen.getPrivateKey(), gen.getP());
		assertEquals(gen.getPublicKey(), verif);
	}
}
