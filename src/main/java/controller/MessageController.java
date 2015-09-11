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
package controller;

import model.Application;
import model.data.user.Conversations;
import model.data.user.User;
import model.data.user.UserMessage;
import util.secure.AsymKeysImpl;

/**
 * User message sender controller.
 * @author Prudhomme Julien
 *
 */
public class MessageController {
	
	private UserMessage msg;
	private User u;
	
	public MessageController(String subject, String message, AsymKeysImpl to) {
		u = Application.getInstance().getManager().getUserManager().getCurrentUser();
		if(u == null) {
			throw new RuntimeException("Must be loggued");
		}
		msg = new UserMessage(to, u.getKeys(), subject, message);
	}
	
	public boolean send() {
		
		Application.getInstance().getManager()
				.getMessageManager().getCurrentUserConversations().addMessage(msg);
		
		msg.encrypt();
		msg.sign(u.getKeys());
		Application.getInstance().getManager().getMessageManager().addMessage(msg);
		
		//TODO TRY DIRECT SENDING
		
		return false;
	}
}
