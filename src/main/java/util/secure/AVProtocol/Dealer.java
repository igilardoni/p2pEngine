package util.secure.AVProtocol;

import java.math.BigInteger;
import java.util.ArrayList;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import util.secure.ElGamalEncrypt;

public class Dealer {

	private AsymKeysImpl keys ;
	
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
	
	public void EncryptForPartipantI(int i, BigInteger Mi, TTP TTP )
	{
		ParticipantEx Pi = TTP.getParticipant(i);
		ElGamal elGamal = new ElGamal(Pi.getKeys());
		Pi.setMi(elGamal.encryptForContract(Mi.toByteArray()));
	}

	
	public Delta CreateDelta(int n, BigInteger m, TTP TTP, ArrayList <BigInteger>aj)
	{
		ArrayList <BigInteger> mi = new ArrayList <BigInteger>();
		for (int i =0; i< TTP.getN(); i++)
		{
			mi.add(createMi(m, TTP.getParticipant(i), aj, keys.getP()));
			EncryptForPartipantI(i, mi.get(i), TTP);
		}
		Proof proof = new Proof (m, keys.getG(), keys.getP(), mi, aj);
		return new Delta (proof, TTP);
	}

}
