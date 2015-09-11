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
package model.network.search.extended;

import model.data.user.User;

/**
 * Filter users results
 * @author Julien Prudhomme
 *
 */
public class UserFilter extends BaseExtendedListener<User>{

	private String publicKey = null;
	private String username = null;
	private String name = null;
	private String firstname = null;
	
	
	/**
	 * Create a new UserFilter instance
	 * @param publicKey
	 * @param username
	 * @param name
	 * @param firstName
	 */
	public UserFilter(String publicKey, String username, String name, String firstname) {
		this.publicKey = publicKey;
		this.username = username;
		this.name = name;
		this.firstname = firstname;
	}
	
	@Override
	public boolean filter(User event) {
		
		if(!(publicKey != null && event.getKeys().getPublicKey().toString(16).equals(publicKey))) {
			return false;
		}
		
		if(!(username != null && event.getNick().equals(username))) {
			return false;
		}
		
		if(!(name != null && event.getName().equals(name))) {
			return false;
		}
		
		if(!(firstname != null && event.getFirstName().equals(firstname))) {
			return false;
		}
		
		return true;
	}

}
