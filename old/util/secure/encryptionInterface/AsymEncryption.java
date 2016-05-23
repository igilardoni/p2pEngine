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

/**
 * public interface for encrypt/ decrypt operation with an asymetric algorithm.
 * The interface isn't dependant on the algorithm.
 * @author Julien Prudhomme
 *
 * @param <K> The type of private and public keys (Typically a BigInteger)
 * @param <D> The type of crypted or decrypted data (Typically byte[])
 */
public interface AsymEncryption<D, K> {
	
	
	/**
	 * Set the public/private key if known, and the right P & G if needed
	 * @param keys
	 */
	public void setAsymsKeys(AsymKeys<K> keys);
	
	/**
	 * Encrypt the data with a public key.
	 * @param data the data to encrypt
	 * @param publicKey the public key
	 * @return the data encrypted
	 */
	public D encryptWithPublicKey(D data);
	
	/**
	 * Decrypt the data with the private key
	 * @param data the data encrypted
	 * @param privateKey the private key
	 * @return the data decrypted
	 */
	public D decryptWithPrivateKey(D data);
}
