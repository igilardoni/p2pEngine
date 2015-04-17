package util.secure;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;
/*
 * TODO NOT FINISHED
 */
public class KeyGeneratorTest {

	@Test(timeout=180000)
	public void GeneratorOfTwinKeys() {
		AsymKeysImpl gen;
		BigInteger verif;
		
		gen = new AsymKeysImpl(false);
		verif = gen.getG().modPow(gen.getPrivateKey(), gen.getP());
		assertEquals(gen.getPublicKey(), verif);
		
		gen = new AsymKeysImpl(true);
		verif = gen.getG().modPow(gen.getPrivateKey(), gen.getP());
		assertEquals(gen.getPublicKey(), verif);
	}
}
