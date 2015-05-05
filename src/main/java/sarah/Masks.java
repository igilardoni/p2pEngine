package sarah;

import java.math.BigInteger;

/*
 * TODO NEED DESCRIPTION !
 */
public class Masks {

	private BigInteger a;
	private BigInteger aBis;

	public Masks (BigInteger a, BigInteger aBis) {
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
