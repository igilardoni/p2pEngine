package util.secure.sigmaProtocol;


import java.math.BigInteger;

/**
 * It's the result of encryption 
 * @author sarah
 *
 */
public class ResEncrypt {
	private BigInteger u;
	private BigInteger v;
	private byte[] M;
	
	/**
	 * Constructor
	 * @param u
	 * @param v
	 * @param M
	 */
	public ResEncrypt(BigInteger u, BigInteger v, byte[] M)
	{
		this.setU(u);
		this.setV(v);
		this.setM(M);
	}

	public BigInteger getU() {
		return u;
	}

	public void setU(BigInteger u) {
		this.u = u;
	}

	public BigInteger getV() {
		return v;
	}

	public void setV(BigInteger v) {
		this.v = v;
	}

	public byte[] getM() {
		return M;
	}

	public void setM(byte[] m) {
		M = m;
	}
	
}
