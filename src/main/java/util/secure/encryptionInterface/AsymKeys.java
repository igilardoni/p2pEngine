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
package util.secure.encryptionInterface;

import java.math.BigInteger;

/**
 * Interface for asymetric encryption.
 * @author Julien Prudhomme
 *
 * @param <K> the datas' type
 */
public interface AsymKeys<K> {
	
	/**
	 * Generate the private and public key according to the implemented asymmetric algorithm
	 * @param password The password to encrypt private key.
	 * @return True if successful generation
	 */
	public boolean generate(String password);
	
	
	
	/**
	 * Get the publicKey
	 * @return
	 */
	public K getPublicKey();
	
	
	/**
	 * Get the privateKey
	 * @return
	 */
	public K getPrivateKey();
	
	/**
	 * Get G of the publicKey
	 * @return
	 */
	public BigInteger getG();
	
	/**
	 * Get P of the publicKey
	 * @return
	 */
	public BigInteger getP();
}
