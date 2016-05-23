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

import model.data.contrat.Contrat;
import model.data.manager.Manager;
import model.network.communication.Communication;
import model.network.communication.service.InstanceSender.ContratSender;
import model.network.search.RandomPeerFinder;
import model.network.search.Search;
import net.jxta.peer.PeerID;

public class ContratsResiliance extends AbstractResiliance {

	public ContratsResiliance(Manager m, Communication c) {
		super(m, c);
	}

	@Override
	public void step() {
		Search<Contrat> s = new Search<Contrat>(manager.getNetwork(), Contrat.class.getSimpleName(), "keyId", true);
		for(Contrat f : manager.getContratManager().getContrats()) {
			s.search(f.getId(), 2, 5);
			for(Contrat contrat: s.getResults()) {
				if(!contrat.checkSignature(f.getKeys())) {
					s.getResults().remove(contrat);
				}
				else {
					if(contrat.getLastUpdated() > f.getLastUpdated()) {
							manager.getContratManager().addContrat(contrat);
							f = contrat;
					}
				}
			}
			if(s.getResults().size() < 5) {
				RandomPeerFinder rpf = new RandomPeerFinder(manager.getNetwork());
				rpf.findPeers(2, 5 - s.getResults().size());
				com.getService(ContratSender.class.getSimpleName()).sendMessage(f, rpf.getResults().toArray(new PeerID[0]));
			}
			f.publish(manager.getNetwork());
		}
	}

}
