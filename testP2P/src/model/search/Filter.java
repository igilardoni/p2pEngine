package model.search;


import model.Advertisable;
import model.Objet;
import model.ObjetsManagement;

public class Filter extends BaseListenerTalker{
	private boolean offre = false;
	private boolean demande = false;
	private String user = null;
	
	public void setOffreFilter(boolean offre) {
		this.offre = offre;
	}
	
	public void setDemandeFilter(boolean demande) {
		this.demande = demande;
	}
	
	public void setUserFilter(String user) {
		this.user = user;
	}
	
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
			this.notifyListener(obj); //on notifie les potentiels écouteurs qu'on a accepté un nouveau objet.
		}
	}

	@Override
	public void searchEvent(Advertisable adv) {
		addObjet((Objet) adv);
	}
	
}
