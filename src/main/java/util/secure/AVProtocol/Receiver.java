package util.secure.AVProtocol;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import util.secure.AsymKeysImpl;

public class Receiver {
	
	private AsymKeysImpl keys ;
	
	public AsymKeysImpl getKeys() {
		return keys;
	}

	public void setKeys(AsymKeysImpl keys) {
		this.keys = keys;
	}
	
	/**
	 * check the proof 
	 * @param delta
	 * @param Dkeys
	 * @return
	 */
	public Boolean Verifies (Delta delta, AsymKeysImpl Dkeys)
	{
		TTP ttp = delta.getTtp();
		Proof proof = delta.getProof();
		
		for (ParticipantEx Pi : ttp.getParticipants())
		{
			BigInteger x = BigInteger.ONE;
			BigInteger produit = BigInteger.ONE;
			for (BigInteger A : proof.getListAj())
			{
				x = x.multiply(Pi.getX());
				produit = produit.multiply(A.modPow(x, Dkeys.getP()));
			}
			if (proof.getMapMi(Pi) != proof.getM().multiply(produit))
				return false;
		}
		
		return true;
	}
	
	public BigInteger Reconstruction (TTP ttp)
	{
		BigInteger [] x = new BigInteger [ttp.getK()];
		BigInteger [] mi = new BigInteger [ttp.getK()];;
		
		for (int i = 0 ; i< ttp.getParticipants().size(); i++)
		{
			ParticipantEx Pi = ttp.getParticipant(i);
			x[i] = Pi.getX();
			mi[i] = new BigInteger (Pi.getMiD());
		}
		
		return Lagrange.inter( x, mi, BigInteger.ZERO);
	}
	
}
