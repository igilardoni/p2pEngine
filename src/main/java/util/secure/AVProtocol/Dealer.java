package util.secure.AVProtocol;

import java.math.BigInteger;
import java.util.ArrayList;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import util.secure.ElGamalEncrypt;

/**
 * person begin the protocol
 * @author sarah
 *
 */
public class Dealer {

	private AsymKeysImpl keys ;
	
	/**
	 * create message i destinate to external participant i in the conflict
	 * @param m
	 * @param Pi
	 * @param aj
	 * @param p
	 * @return
	 */
	public BigInteger createMi (BigInteger m, ParticipantEx Pi , ArrayList <BigInteger>aj, BigInteger p)
	{
		BigInteger Mi = BigInteger.ZERO;
		BigInteger powX = BigInteger.ONE;
		
		for (int i = 1; i<= aj.size(); i++)
		{
			powX = powX.multiply(Pi.getX());  
			Mi = Mi.add((aj.get(i-1)).multiply(powX));
		}
		Mi = Mi.add(m);
		return Mi.mod(p);
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
			BigInteger Mi = createMi(m, TTP.getParticipant(i), aj, keys.getP());
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
