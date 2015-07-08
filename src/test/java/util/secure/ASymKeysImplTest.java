package util.secure;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class ASymKeysImplTest {

	@Test(timeout=180000)
	public void Generation() {
		AsymKeysImpl gen;
		BigInteger verif;
		
		gen = new AsymKeysImpl("");
		verif = gen.getG().modPow(gen.getPrivateKey(), gen.getP());
		assertEquals(gen.getPublicKey(), verif);
		// TODO Reduction time duration !
		/*gen = new AsymKeysImpl(true);
		verif = gen.getG().modPow(gen.getPrivateKey(), gen.getP());
		assertEquals(gen.getPublicKey(), verif);*/
	}
	
	@Test
	public void isCompatible(){
		AsymKeysImpl gen = new AsymKeysImpl();
		assertFalse(gen.isCompatible());
		
		gen = new AsymKeysImpl("");
		assertTrue(gen.isCompatible());
	}
}
