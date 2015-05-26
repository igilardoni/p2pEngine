package sarah;

import java.math.BigInteger;

/**
 * class for the set of public Keys 
 * @author sarah
 *
 */

public class Keys {
	
	private BigInteger p;
	private BigInteger g;
	private BigInteger publicKey;
	
/**
 * Constructor
 * p and g is fixed
 */
	public Keys()
	{
		setP(new BigInteger ("124233341635855292420681698148845681014844866056212176632655173602444135581779341928584451946831820357622587249219477577145009300106828967466602146104562163160400103396735672041344557638270362523343149686623705761738910044071399582025053147811261321814632661084042311141045136246602979886564584763268994320823"));
		setG(new BigInteger ("57879985263161130068016239981615161174385902716647642452899971198439084259551250230041086427537114453738884538337956090286524329552098304591825815816298805245947460536391128315522193556464285417135160058086869161063941463490748168352401178939129440934609861888674726565294073773971086710395310743717916632171"));
	}
	
/**
 * Constructor
 * p, g and publicKey is fixed
 * @param p
 * @param g
 * @param publicKey
 */
	public Keys(BigInteger p, BigInteger g, BigInteger publicKey)
	{
		this.setP(p);
		this.setG(g);
		this.setPublicKey(publicKey);
	}
	
/**
 * Constructor
 * All keys have to generate
 * @param gen
 */
	public Keys(Boolean gen)
	{
		// to do
	}

/**
 * generate of publicKey whith privateKey, and p, and g
 * @param privateKey
 * @return
 */
	protected BigInteger generatePublicKeys(BigInteger privateKey)
	{
		return  getG().modPow(privateKey,getP());
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}

	public BigInteger getG() {
		return g;
	}

	public void setG(BigInteger g) {
		this.g = g;
	}

	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}
}
