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
		Search<Contrat> s = new Search<Contrat>(manager.getNetwork().getGroup("Contrat").getDiscoveryService(), "keyId", true);
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
			f.publish(manager.getNetwork().getGroup("Contrat"));
		}
	}

}
