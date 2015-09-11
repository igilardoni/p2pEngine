/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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
