package model.network.communication.service.sigma.sigmaProtocol;

import java.math.BigInteger;

import org.jdom2.Element;

import util.StringToElement;

/**
 * Mask to send in the protocolSigma
 * @author sarah bourkis
 * @author Julien Prudhomme
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
	
	public Masks(String xml) {
		this(StringToElement.getElementFromString(xml));
	}
	
	public Masks(Element root) {
		for(Element e: root.getChildren()) {
			if(e.getName().equals("a")) {
				this.a = new BigInteger(e.getValue(), 16);
			}
			else if(e.getName().equals("aBis")) {
				this.aBis = new BigInteger(e.getValue(), 16);
			}
		}
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
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("<" + this.getClass().getSimpleName().toLowerCase() + ">");
		s.append("<a>" + a.toString(16) + "</a>");
		s.append("<aBis>" + aBis.toString(16) + "</aBis>");
		s.append("</" + this.getClass().getSimpleName().toLowerCase() + ">");
		return s.toString();
	}
}
