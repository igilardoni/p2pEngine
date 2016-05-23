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
package model.data.manager.resiliance;

import net.jxta.peer.PeerID;
import model.data.manager.Manager;
import model.data.user.User;
import model.network.communication.Communication;
import model.network.communication.service.InstanceSender.UserSender;
import model.network.search.RandomPeerFinder;
import model.network.search.Search;

public class UserResiliance extends AbstractResiliance {

	public UserResiliance(Manager m, Communication c) {
		super(m, c);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void step() {
		Search<User> s = new Search<User>(manager.getNetwork(), User.class.getSimpleName(), "publicKey", true);
		for(User u: manager.getUserManager().getUsers()) {
			s.search(u.getKeys().getPublicKey().toString(16), 2, 5);
			for(User user: s.getResults()) {
				if(!user.checkSignature(u.getKeys())) {
					s.getResults().remove(user); //wrong signature, result doesn't count.
				}
				else {
					if(u.getLastUpdated() < user.getLastUpdated()) {
						manager.getUserManager().addUser(user, false);
						u = user;
					}
				}
			}
			if(s.getResults().size() < 5) {
				RandomPeerFinder rpf = new RandomPeerFinder(manager.getNetwork());
				rpf.findPeers(2, 5 - s.getResults().size());
				com.getService(UserSender.class.getSimpleName()).sendMessage(u, rpf.getResults().toArray(new PeerID[0]));
			}
			u.publish(manager.getNetwork());
		}
	}

}
