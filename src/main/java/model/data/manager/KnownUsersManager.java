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
package model.data.manager;

import java.util.HashMap;

import model.data.favorites.KnownUsers;
import model.data.user.User;
import util.Printer;

public class KnownUsersManager {
	private HashMap<String, KnownUsers> knownUsers = new HashMap<>();
	private Manager m;
	
	public KnownUsersManager(Manager m) {
		this.m = m;
	}
	
	/**
	 * Get the current user's user favorites.
	 * @return
	 */
	public KnownUsers getCurrentUserKnownUsers() {
		if(m.getUserManager().getCurrentUser() == null) {
			Printer.printError(this, "getCurrentUserKnownUsers", "no users logged");
		}
		User currentUser = m.getUserManager().getCurrentUser();
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!knownUsers.containsKey(publicKey)) knownUsers.put(publicKey, new KnownUsers(currentUser));
		return knownUsers.get(publicKey);
	}
	
}
