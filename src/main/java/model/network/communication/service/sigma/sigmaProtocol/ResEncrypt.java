package model.network.communication.service.sigma.sigmaProtocol;


import java.math.BigInteger;

import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;

/**
 * It's the result of encryption 
 * @author sarah
 *
 */
public class ResEncrypt extends AbstractAdvertisement {
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
		super();
		this.setU(u);
		this.setV(v);
		this.setM(M);
	}
	
	public ResEncrypt(String xml) {
		super(xml);
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

	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}

	@Override
	protected String getAdvertisementName() {
		return getClass().getName();
	}

	@Override
	protected void setKeys() {
		addKey("u", false, false);
		addKey("v", false, false);
		addKey("M", false, false);
		
	}

	@Override
	protected void putValues() {
		addValue("u", u.toString(16));
		addValue("v", v.toString(16));
		addValue("M", M.toString());
	}

	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "u": setU(new BigInteger(e.getValue(), 16)); return true;
		case "v": setV(new BigInteger(e.getValue(), 16)); return true;
		case "M": setM(e.getValue().getBytes()); return true;
		default: return false;
		}
	}
	
}
