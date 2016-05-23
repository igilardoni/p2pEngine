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

import java.security.InvalidKeyException;

import util.secure.encryptionInterface.SymEncryption;
import Serpent.Serpent_BitSlice;

/**
 * Serpent implementation for various length data.
 * @author Julien Prudhomme
 *
 */
public class Serpent implements SymEncryption<byte[], String>{
	private Object secretKey = null;
	private static final int BLOCK_SIZE = 16;


	/**
	 * Create a new Serpent instance with the specified secret key.
	 * @param secretKey the secret key.
	 */
	public Serpent(String secretKey) {
		setSecretKey(secretKey);
	}

	/**
	 * Modify the plain text to get a multiple of 16 bits
	 * @param data
	 * @return
	 */
	private byte[] getValidsBlocks(byte[] data) {
		int lenght = ((data.length - 1)/BLOCK_SIZE + 1)*BLOCK_SIZE;
		byte[] res = new byte[lenght + 1];
		byte added = 0;
		for(int i = 0; i < lenght; i++) {
			if(i >= data.length) {
				res[i] = 0;
				added++;
			}
			else res[i] = data[i];
		}
		res[lenght] = added;
		return res;
	}


	/**
	 * Encrypt the datas
	 * @param plainText the original datas
	 * @return the data encrypted.
	 */
	public byte[] encrypt(byte[] plainText) {
		byte[] validPlainTest = getValidsBlocks(plainText);
		byte[] res =  new byte[validPlainTest.length];
		for(int i = 0; i < validPlainTest.length - 1; i+= BLOCK_SIZE) {
			byte[] cypher = Serpent_BitSlice.blockEncrypt(validPlainTest, i, secretKey);
			for(int j = 0; j < cypher.length; j++) {
				res[i + j] = cypher[j];
			}
		}
		res[validPlainTest.length - 1] = validPlainTest[validPlainTest.length - 1];
		return res;
	}
	

	/**
	 * Decrypt the encrypted datas
	 * @param cypher the encrypted datas
	 * @return the original data
	 */
	public byte[] decrypt(byte[] cypher) {
		byte added = cypher[cypher.length - 1];
		byte[] res = new byte[cypher.length - added - 1];
		for(int i = 0; i < cypher.length - 1; i+= BLOCK_SIZE) {
			byte[] plainText = Serpent_BitSlice.blockDecrypt(cypher, i, secretKey);
			for(int j = 0; j < plainText.length; j++) {
				if(i+j >= res.length) break;
				res[i + j] = plainText[j];
			}
		}
		
		return res;
	}


	public static void main(String[] args) {
		String plainText = "Lorem ipsum dolor sit amet ! Izy";
		//String plainText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sit amet ex ex. Sed vel nibh non dolor dignissim pretium. Aliquam eu suscipit arcu, sed vestibulum lorem. Fusce scelerisque eleifend eleifend. Donec tempus quis felis sit amet vehicula. Aenean ut porttitor nibh, consectetur efficitur metus. Nam pulvinar aliquam laoreet. Ca marche ?";
		String password = "qosfqifquig";

		System.out.println("Original");
		System.out.println("============================");
		System.out.println(plainText);
		System.out.println("============================\n\n");

		Serpent s = new Serpent(password);
		byte[] cypher = s.encrypt(plainText.getBytes());

		System.out.println("Cypher");
		System.out.println("============================");
		System.out.println(new String(cypher));
		System.out.println("============================\n\n");
		byte[] decrypt = s.decrypt(cypher);

		System.out.println("Decrypted");
		System.out.println("============================");
		System.out.println(new String(decrypt));
		System.out.println("============================");
		
		if(plainText.equals(new String(decrypt))) {
			System.out.println("ok !");
		}
	}

	@Override
	public void setSecretKey(String key) {
		try {
			this.secretKey = Serpent_BitSlice.makeKey(key.getBytes());
		} catch (InvalidKeyException e) {
			System.err.println("---- invalid key ----");
			e.printStackTrace();
		}
	}

}