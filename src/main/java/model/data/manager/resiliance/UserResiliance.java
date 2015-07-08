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
		Search<User> s = new Search<User>(manager.getNetwork().getGroup("users").getDiscoveryService(), "publicKey", true);
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
			u.publish(manager.getNetwork().getGroup("users"));
		}
	}

}
