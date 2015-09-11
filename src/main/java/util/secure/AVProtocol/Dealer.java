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
import java.util.ArrayList;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import util.secure.ElGamalEncrypt;
import util.secure.AVProtocol.ParticipantEx;

/**
 * person begin the protocol
 * @author sarah
 *
 */
public class Dealer {

	private AsymKeysImpl keys ;
	
	public Dealer(AsymKeysImpl key){
		if(!key.isCompatible())
			try {
				throw new Exception("Incompatible key !");
			} catch (Exception e) {
				e.printStackTrace();
			}
		this.keys = key;
	}
	
	/**
	 * create message i destinate to external participant i in the conflict
	 * @param m
	 * @param Pi
	 * @param aj
	 * @param p
	 * @return
	 */
	public BigInteger createMi (BigInteger m, ParticipantEx Pi , ArrayList <BigInteger>aj)
	{
		BigInteger Mi = BigInteger.ZERO;
		BigInteger powX = BigInteger.ONE;
		
		for (int i = 1; i<= aj.size(); i++)
		{
			powX = powX.multiply(Pi.getX());  
			Mi = Mi.add((aj.get(i-1)).multiply(powX));
		}
		Mi = Mi.add(m);
		return Mi.mod(keys.getP());
	}
	
	/**
	 * encrypt message i for the external participant i
	 * @param i
	 * @param Mi
	 * @param TTP
	 */
	public void EncryptForPartExI(int i, BigInteger Mi, TTP TTP )
	{
		ParticipantEx Pi = TTP.getParticipant(i);
		ElGamal elGamal = new ElGamal(Pi.getKeys());
		Pi.setMi(elGamal.encryptWithPublicKey(Mi.toByteArray()));
	}

	/**
	 * create delta to send to the receiver constitute (proof and all mi encrypted)
	 * @param n
	 * @param m
	 * @param TTP
	 * @param aj
	 * @return
	 */
	public Delta CreateDelta(int n, BigInteger m, TTP TTP, ArrayList <BigInteger>aj)
	{
		Proof proof = new Proof (m, keys.getG(), keys.getP(), aj);
		
		for (int i =0; i< TTP.getN(); i++)
		{
			BigInteger Mi = createMi(m, TTP.getParticipant(i), aj);
			EncryptForPartExI(i, Mi, TTP);
			proof.addMi(TTP.getParticipant(i), Mi);
		}
		return new Delta (proof, TTP);
	}
	
	/**
	 * encrypt for recceiver in the end of protocol
	 * @param i
	 * @param M
	 * @param receiver
	 * @return
	 */
	public byte[] EncryptForReceiverI(int i, BigInteger M, Receiver receiver )
	{
		ElGamal elGamal = new ElGamal(receiver.getKeys());
		return elGamal.encryptWithPublicKey(M.toByteArray());
	}
	

}
