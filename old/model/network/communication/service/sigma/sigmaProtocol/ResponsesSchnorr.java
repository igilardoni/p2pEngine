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

import util.secure.AsymKeysImpl;

/**
 * The Schnorr response
 * @author sarah
 *
 */
public class ResponsesSchnorr extends Responses{

	/**
	 * Constructo
	 * @param mask
	 * @param challenge
	 * @param response
	 */
	public ResponsesSchnorr(Masks mask, BigInteger challenge,
			BigInteger response) {
		super(mask, challenge, response);
	}
	
	public ResponsesSchnorr(String xml) {
		super(xml);
	}

	@Override
	/**
	 * Extends Responses
	 * Verify if the Schnorr response is good or not 
	 */
	public Boolean Verifies(AsymKeysImpl tKeys, ResEncrypt res) {
		return (tKeys.getG().modPow(getResponse(), tKeys.getP()).equals(((tKeys.getPublicKey().modPow(getChallenge(), tKeys.getP())).multiply(getMasks().getA())).mod(tKeys.getP())));
	}

	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}

	@Override
	protected String getAdvertisementName() {
		return getClass().getName();
	}
	

}
