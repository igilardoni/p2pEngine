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
package model.advertisement;

import org.jdom2.Element;

/**
 * Short representing of an advertisement (just Id & publicKey) to save network data
 * @author Julien Prudhomme
 * TODO IdAdvertisement not finished ?
 */
public class IdAdvertisement extends AbstractAdvertisement{

	
	public IdAdvertisement(AbstractAdvertisement adv) {
		super();
		this.setId(adv.getId());
		this.setKeys(adv.getKeys());
	}
	
	@Override
	protected String getAdvertisementName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void setKeys() {
	}

	@Override
	protected void putValues() {
	}

	@Override
	protected boolean handleElement(Element e) {
		return false;
	}

	@Override
	public String getSimpleName() {
		return null;
	}

}
