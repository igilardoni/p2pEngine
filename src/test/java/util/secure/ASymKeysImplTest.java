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
