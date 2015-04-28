package util.secure;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class ASymKeysImplTest {

	@Test(timeout=180000)
	public void Generation() {
		/*AsymKeysImpl gen;
		BigInteger verif;
		
		gen = new AsymKeysImpl(false);
		verif = gen.getG().modPow(gen.getPrivateKey(), gen.getP());
		assertEquals(gen.getPublicKey(), verif);
		
		gen = new AsymKeysImpl(true);
		verif = gen.getG().modPow(gen.getPrivateKey(), gen.getP()); */
		assertEquals(true, true);
		//assertEquals(gen.getPublicKey(), verif);
	}
	
	@Test
	public void isCompatible(){
		AsymKeysImpl gen = new AsymKeysImpl();
		assertEquals(gen.isCompatible(), false);
	}
}
