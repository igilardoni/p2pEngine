package model.search;


import net.jxta.discovery.DiscoveryService;
import model.Objet;
import model.ObjetsManagement;
import model.User;
import model.advertisements.Advertisable;

/**
 * Filtre simple sur les champ d'un objet
 * Voir ensuite BaseListenerTalker
 * @author Prudhomme Julien
 *
 */
public class Filter extends BaseListenerTalker{
	private boolean offre = false;
	private boolean demande = false;
	private String user = null;
	private DiscoveryService discovery;
	
	/**
	 * Creer le filtre
	 * @param discovery Le discovery service d'un peer
	 */
	public Filter(DiscoveryService discovery) {
		this.discovery = discovery;
	}
	
	/**
	 * Si offre = true, alors seul les objet étant des propositions seront retenus
	 * @param offre
	 */
	public void setOffreFilter(boolean offre) {
		this.offre = offre;
	}
	
	/**
	 * Si demande = true, alors seul les objets étant des souhaits seront retenus
	 * @param demande
	 */
	public void setDemandeFilter(boolean demande) {
		this.demande = demande;
	}
	
	/**
	 * Si l'utilisateur est défini, alors seul les annonces ayant pour auteur cet utilisateur 
	 * seront retenus.
	 * @param user
	 */
	public void setUserFilter(String user) {
		this.user = user;
	}
	
	/**
	 * Ajoute un objet au filtre, qui l'acceptera ou non
	 * @param obj
	 */
	public void addObjet(Objet obj) {
		
		if(offre) {
			if(!obj.isProposition()) return;
		}
		
		if(demande) {
			if(!obj.isSouhait()) return;
		}
		
		if(user != null) {
			if(!obj.getUserName().equals(user)) return;
		}
		
		if(!objets.contains(obj)) {
			objets.add(obj);
			RemoteRessource<User> rs = new RemoteRessource<User>(discovery, "miniLogin", 1000);
			obj.setUser(rs.getRemoteRessource(obj.getUserName()));
			this.notifyListener(obj); //on notifie les potentiels écouteurs qu'on a accepté un nouveau objet.
		}
	}

	@Override
	public void searchEvent(Advertisable adv) {
		addObjet((Objet) adv);
	}
	
}
