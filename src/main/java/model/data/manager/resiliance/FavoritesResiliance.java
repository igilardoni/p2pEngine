package model.data.manager.resiliance;

import net.jxta.peer.PeerID;
import model.data.favorites.Favorites;
import model.data.manager.Manager;
import model.network.communication.Communication;
import model.network.communication.service.InstanceSender.FavoritesSender;
import model.network.search.RandomPeerFinder;
import model.network.search.Search;


/**
 * Retrieve and replicate favories.
 * @author Julien Prudhomme
 *
 */
public class FavoritesResiliance extends AbstractResiliance {

	public FavoritesResiliance(Manager m, Communication c) {
		super(m, c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void step() {
		Search<Favorites> s = new Search<Favorites>(manager.getNetwork(), Favorites.class.getSimpleName(), "keyId", true);
		for(Favorites f : manager.getFavoriteManager().getFavorites()) {
			s.search(f.getId(), 2, 5);
			for(Favorites favorites: s.getResults()) {
				if(!favorites.checkSignature(f.getKeys())) {
					s.getResults().remove(favorites);
				}
				else {
					if(favorites.getLastUpdated() > f.getLastUpdated()) {
							manager.getFavoriteManager().addFavorites(favorites);
							f = favorites;
					}
				}
			}
			if(s.getResults().size() < 5) {
				RandomPeerFinder rpf = new RandomPeerFinder(manager.getNetwork());
				rpf.findPeers(2, 5 - s.getResults().size());
				com.getService(FavoritesSender.class.getSimpleName()).sendMessage(f, rpf.getResults().toArray(new PeerID[0]));
			}
			f.publish(manager.getNetwork());
		}
	}

}
