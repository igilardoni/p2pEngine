package sarah;

import java.math.BigInteger;

/**
 * Mask to send in the protocolSigma
 * @author sarah
 *
 */

public class Masks {

	private BigInteger a;
	private BigInteger aBis;
	
	/**
	 * Constructor
	 * @param a
	 * @param aBis
	 */
	public Masks (BigInteger a, BigInteger aBis)
	{
		this.setA(a);
		this.setaBis(aBis);
	}

	public BigInteger getA() {
		return a;
	}

	public void setA(BigInteger a) {
		this.a = a;
	}

	public BigInteger getaBis() {
		return aBis;
	}

	public void setaBis(BigInteger aBis) {
		this.aBis = aBis;
	}
}
