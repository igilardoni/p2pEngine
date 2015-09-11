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
package view.interlocutors.user;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.SearchUserController;
import model.Application;
import model.data.favorites.KnownUsers;
import model.data.user.User;
import util.Printer;
import view.interlocutors.AbstractInterlocutor;

public class addToKnownUsers extends AbstractInterlocutor {

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String nick = c.getString("nick");
			String publicKey = c.getString("publickey");
			KnownUsers ku = Application.getInstance().getManager().getKnownUsersManager().getCurrentUserKnownUsers();
			SearchUserController su = new SearchUserController();
			su.startSearchByPublicKey(publicKey);
			User result = su.getPublicKeyResult();
			if(result == null) {
				Printer.printError(this, "run", "no result");
			}
			else {
				ku.add(nick, result);
				System.out.println("User " + result.getNick() + "added to known user");
			}
			
		} catch (JSONException e) {
				e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
