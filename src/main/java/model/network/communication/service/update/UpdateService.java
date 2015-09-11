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
package model.network.communication.service.update;

import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;
import model.network.communication.service.Service;

public class UpdateService extends Service<UpdateMessage>{

	

	
	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}


	@Override
	public void sendMessage(UpdateMessage data, PeerID... ids) {
		switch(data.getType()) {
		case "User": break;
		case "Item": break;
		default:     break;
		}
		
		sender.sendMessage(data.toString(), this.getClass().getSimpleName(), null);
		
	}

	
	private UpdateMessage handleUser(UpdateMessage m) {
		
		
		return m;
	}
	
	private UpdateMessage handleItem(UpdateMessage m) {
		
		
		
		return m;
	}

	@Override
	public UpdateMessage handleMessage(Message m) {
		String content = new String(m.getMessageElement("content").getBytes(true));
		UpdateMessage update = new UpdateMessage(content);
		switch(update.getType()) {
		case "User":   return handleUser(update);
		case "Item": return handleItem(update);
		default: return null;
		}
	}

}