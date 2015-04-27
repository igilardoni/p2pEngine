package util.secure;

import java.math.BigInteger;

/**
 * An ElGamal signature
 * @author Julien Prudhomme
 *
 */
public class ElGamalSign {
	private BigInteger r;
	private BigInteger s;
	
	/**
	 * Initialize the ElGamal signature with the right R & S
	 * @param r
	 * @param s
	 */
	public ElGamalSign(BigInteger r, BigInteger s) {
		this.r = r;
		this.s = s;
	}
	
	public BigInteger getR() {
		return r;
	}
	
	public BigInteger getS() {
		return s;
	}
	
	public String toString() {
		return "<signR>" + r.toString(16) + "</signR>" +
				"<signS>" + s.toString(16) + "</signS>";
	}
}
