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
package util.secure.AVProtocol;

import java.math.BigInteger;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import util.secure.ElGamalEncrypt;

/**
 * each Participant in set TTP
 * public key's particpant, message mi encrypt for him, number in the set, x publicly element 
 * @author sarah
 *
 */

public class ParticipantEx {

	private AsymKeysImpl keys ;
	private BigInteger x;
	private int number ;
	private byte[] Mi;
	private byte [] miD;
	
	public ParticipantEx (AsymKeysImpl keys, int number, BigInteger x)
	{
		this.setKeys(keys);
		this.setNumber(number);
		this.setX(x);
	}

	public AsymKeysImpl getKeys() {
		return keys;
	}

	public void setKeys(AsymKeysImpl keys) {
		this.keys = keys;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public byte[] getMi() {
		return Mi;
	}

	public void setMi(byte[] mi) {
		Mi = mi;
	}

	public BigInteger getX() {
		return x;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}
	
	public void decryptMi ()
	{
		ElGamal elGamal = new ElGamal (keys);
		setMiD(elGamal.decryptWithPrivateKey(Mi));
	}

	public byte [] getMiD() {
		return miD;
	}

	public void setMiD(byte [] miD) {
		this.miD = miD;
	}
}